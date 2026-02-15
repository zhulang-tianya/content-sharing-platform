#!/usr/bin/env python3
"""
代码规范检查脚本

用于检查Java代码是否符合内容分享平台开发规范。

Usage:
    python code-check.py <file_or_directory>
    python code-check.py path/to/File.java
    python code-check.py path/to/src/main/java
"""

import os
import re
import sys
from pathlib import Path
from dataclasses import dataclass, field
from typing import List, Optional


@dataclass
class CheckResult:
    """检查结果"""
    file_path: str
    line_number: int
    rule_id: str
    severity: str
    message: str
    
    def __str__(self):
        return f"{self.file_path}:{self.line_number} [{self.severity}] {self.rule_id}: {self.message}"


@dataclass
class FileStats:
    """文件统计"""
    total_lines: int = 0
    code_lines: int = 0
    comment_lines: int = 0
    blank_lines: int = 0
    method_count: int = 0
    class_count: int = 0


class CodeChecker:
    """代码检查器"""
    
    def __init__(self):
        self.results: List[CheckResult] = []
        self.stats: FileStats = FileStats()
    
    def check_file(self, file_path: str) -> List[CheckResult]:
        """检查单个文件"""
        self.results = []
        self.stats = FileStats()
        
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
        except Exception as e:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=0,
                rule_id="FILE001",
                severity="ERROR",
                message=f"无法读取文件: {e}"
            ))
            return self.results
        
        self.stats.total_lines = len(lines)
        
        for i, line in enumerate(lines, 1):
            self._check_line(file_path, i, line, lines)
        
        self._check_file_stats(file_path)
        
        return self.results
    
    def _check_line(self, file_path: str, line_num: int, line: str, all_lines: List[str]):
        """检查单行"""
        stripped = line.strip()
        
        # 统计
        if not stripped:
            self.stats.blank_lines += 1
        elif stripped.startswith(('//', '/*', '*', '/**')):
            self.stats.comment_lines += 1
        else:
            self.stats.code_lines += 1
        
        # 检查行长度
        if len(line.rstrip()) > 120:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="LINE001",
                severity="WARN",
                message=f"行长度超过120字符 ({len(line.rstrip())}字符)"
            ))
        
        # 检查Tab缩进
        if '\t' in line and not line.startswith('\t'):
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="INDENT001",
                severity="WARN",
                message="使用Tab缩进，建议使用4个空格"
            ))
        
        # 检查类名
        class_match = re.search(r'\bclass\s+([A-Za-z][A-Za-z0-9]*)', line)
        if class_match:
            self.stats.class_count += 1
            class_name = class_match.group(1)
            if not class_name[0].isupper():
                self.results.append(CheckResult(
                    file_path=file_path,
                    line_number=line_num,
                    rule_id="NAME001",
                    severity="ERROR",
                    message=f"类名 '{class_name}' 应使用大驼峰命名法"
                ))
        
        # 检查方法名
        method_match = re.search(r'\b(public|private|protected)\s+\w+\s+([a-z][A-Za-z0-9]*)\s*\(', line)
        if method_match:
            self.stats.method_count += 1
            method_name = method_match.group(2)
            if method_name[0].isupper():
                self.results.append(CheckResult(
                    file_path=file_path,
                    line_number=line_num,
                    rule_id="NAME002",
                    severity="ERROR",
                    message=f"方法名 '{method_name}' 应使用小驼峰命名法"
                ))
        
        # 检查常量命名
        const_match = re.search(r'public\s+static\s+final\s+\w+\s+([A-Za-z][A-Za-z0-9]*)', line)
        if const_match:
            const_name = const_match.group(1)
            if not re.match(r'^[A-Z][A-Z0-9_]*$', const_name):
                self.results.append(CheckResult(
                    file_path=file_path,
                    line_number=line_num,
                    rule_id="NAME003",
                    severity="ERROR",
                    message=f"常量名 '{const_name}' 应使用全大写下划线命名法"
                ))
        
        # 检查中文命名
        if re.search(r'[\u4e00-\u9fa5]', line) and not stripped.startswith(('//', '/*', '*')):
            # 排除字符串中的中文
            if not re.search(r'"[^"]*[\u4e00-\u9fa5][^"]*"', line):
                self.results.append(CheckResult(
                    file_path=file_path,
                    line_number=line_num,
                    rule_id="NAME004",
                    severity="WARN",
                    message="代码中包含中文，请检查是否为拼音命名"
                ))
        
        # 检查System.out
        if 'System.out.print' in line or 'System.err.print' in line:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="LOG001",
                severity="WARN",
                message="使用System.out/err输出，建议使用日志框架"
            ))
        
        # 检查printStackTrace
        if '.printStackTrace()' in line:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="LOG002",
                severity="WARN",
                message="使用printStackTrace()，建议使用日志框架记录异常"
            ))
        
        # 检查空catch块
        if stripped == '} catch' or re.match(r'}\s*catch\s*\([^)]+\)\s*{\s*}', line):
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="EXCEPT001",
                severity="ERROR",
                message="空catch块，应处理或记录异常"
            ))
        
        # 检查javax包使用（排除Java标准库）
        if 'import javax.' in line:
            # 排除Java标准库的javax包（如javax.crypto）
            std_javax_packages = ['javax.crypto', 'javax.net', 'javax.security', 'javax.management']
            is_std_package = any(pkg in line for pkg in std_javax_packages)
            if not is_std_package:
                self.results.append(CheckResult(
                    file_path=file_path,
                    line_number=line_num,
                    rule_id="JDK001",
                    severity="ERROR",
                    message="使用javax包，应替换为jakarta包"
                ))
        
        # 检查原始类型
        if re.search(r'\bList\s*<', line) or re.search(r'\bMap\s*<', line):
            pass
        elif re.search(r'\b(new\s+)?List\s*\(', line) or re.search(r'\b(new\s+)?Map\s*\(', line):
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=line_num,
                rule_id="GENERIC001",
                severity="WARN",
                message="使用原始类型，应指定泛型参数"
            ))
    
    def _check_file_stats(self, file_path: str):
        """检查文件统计"""
        # 检查文件行数
        if self.stats.total_lines > 1000:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=0,
                rule_id="SIZE001",
                severity="WARN",
                message=f"文件行数 {self.stats.total_lines} 超过1000行，建议拆分"
            ))
        
        # 检查方法数量
        if self.stats.method_count > 20:
            self.results.append(CheckResult(
                file_path=file_path,
                line_number=0,
                rule_id="SIZE002",
                severity="WARN",
                message=f"方法数量 {self.stats.method_count} 超过20个，建议拆分"
            ))


def check_directory(dir_path: str) -> List[CheckResult]:
    """检查目录下所有Java文件"""
    results = []
    java_files = list(Path(dir_path).rglob('*.java'))
    
    for java_file in java_files:
        checker = CodeChecker()
        file_results = checker.check_file(str(java_file))
        results.extend(file_results)
    
    return results


def print_results(results: List[CheckResult]):
    """打印检查结果"""
    error_count = 0
    warn_count = 0
    
    for result in results:
        print(result)
        if result.severity == "ERROR":
            error_count += 1
        elif result.severity == "WARN":
            warn_count += 1
    
    print("\n" + "=" * 60)
    print(f"检查完成: {len(results)} 个问题")
    print(f"  错误: {error_count}")
    print(f"  警告: {warn_count}")
    
    return error_count


def main():
    if len(sys.argv) < 2:
        print("Usage: python code-check.py <file_or_directory>")
        print("Examples:")
        print("  python code-check.py path/to/File.java")
        print("  python code-check.py path/to/src/main/java")
        sys.exit(1)
    
    target = sys.argv[1]
    
    if not os.path.exists(target):
        print(f"错误: 路径不存在: {target}")
        sys.exit(1)
    
    if os.path.isfile(target):
        checker = CodeChecker()
        results = checker.check_file(target)
    else:
        results = check_directory(target)
    
    error_count = print_results(results)
    sys.exit(1 if error_count > 0 else 0)


if __name__ == "__main__":
    main()
