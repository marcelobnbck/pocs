import argparse
from ai_agent.scanner import scan_dependencies
from ai_agent.advisor import analyze_dependencies
from ai_agent.reporter import generate_report

def run_cli():
    parser = argparse.ArgumentParser(description="NPM AI Auditor")
    parser.add_argument("path", help="Path to the NPM project (folder with package.json)")

    args = parser.parse_args()
    dependencies = scan_dependencies(args.path)
    analysis_results = analyze_dependencies(dependencies)
    report_path = generate_report(args.path, analysis_results)

    print(f"✅ Audit complete! Markdown report saved at: {report_path}")
