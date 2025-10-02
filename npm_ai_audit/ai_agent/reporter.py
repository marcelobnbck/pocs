import os
from datetime import datetime

def generate_report(project_path, results):
    if not results:
        print("🎉 All dependencies are up to date!")
        return None

    report_lines = [
        "# 🧠 NPM AI Audit Report",
        f"**Project Path**: `{project_path}`",
        f"**Generated on**: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}",
        "",
        "| Package | Current | Latest | Risk | Migration Guide | Suggested Fix |",
        "|---------|---------|--------|------|------------------|----------------|"
    ]

    for lib, info in results.items():
        report_lines.append(
            f"| `{lib}` | {info['current']} | {info['latest']} | {info['risk']} "
            f"| [Link]({info['migration_guide']}) | `{info['code_fix']}` |"
        )

    output_path = os.path.join("reports", "audit_report.md")
    os.makedirs(os.path.dirname(output_path), exist_ok=True)

    with open(output_path, "w") as f:
        f.write("\n".join(report_lines))

    return output_path
