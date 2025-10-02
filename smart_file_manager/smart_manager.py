import os
import time
import json
import shutil
from datetime import datetime, timedelta

try:
    from plyer import notification
    NOTIFY_AVAILABLE = True
except ImportError:
    NOTIFY_AVAILABLE = False


CONFIG_FILE = "config.json"
LOG_FILE = "file_organizer.log"


def load_config():
    with open(CONFIG_FILE, "r") as f:
        return json.load(f)


def log_action(message):
    with open(LOG_FILE, "a") as log:
        log.write(f"{datetime.now()} - {message}\n")
    print(message)


def send_notification(title, message, enable=True):
    if enable and NOTIFY_AVAILABLE:
        notification.notify(title=title, message=message, timeout=5)


def move_with_duplicate_handling(src, dest_folder):
    os.makedirs(dest_folder, exist_ok=True)
    filename = os.path.basename(src)
    dest_path = os.path.join(dest_folder, filename)

    counter = 1
    while os.path.exists(dest_path):
        name, ext = os.path.splitext(filename)
        new_name = f"{name}({counter}){ext}"
        dest_path = os.path.join(dest_folder, new_name)
        counter += 1

    shutil.move(src, dest_path)
    log_action(f"Moved: {src} → {dest_path}")
    return dest_path


def organize_file(file_path, config):
    _, ext = os.path.splitext(file_path)
    ext = ext.lower()
    file_size = os.path.getsize(file_path)

    # Check size rules first
    for folder, limit in config.get("size_rules", {}).items():
        if file_size >= limit:
            move_with_duplicate_handling(file_path, os.path.join(config["watch_folder"], folder))
            send_notification("File Moved", f"{os.path.basename(file_path)} → {folder}", config["notifications"])
            return

    # Then check by extension
    for folder, extensions in config["destinations"].items():
        if ext in extensions:
            move_with_duplicate_handling(file_path, os.path.join(config["watch_folder"], folder))
            send_notification("File Moved", f"{os.path.basename(file_path)} → {folder}", config["notifications"])
            return

    # Default → Others
    move_with_duplicate_handling(file_path, os.path.join(config["watch_folder"], "Others"))
    send_notification("File Moved", f"{os.path.basename(file_path)} → Others", config["notifications"])


def cleanup_old_files(config):
    archive_folder = os.path.join(config["watch_folder"], "Archive")
    os.makedirs(archive_folder, exist_ok=True)

    cutoff_date = datetime.now() - timedelta(days=config.get("cleanup_days", 30))

    for root, _, files in os.walk(config["watch_folder"]):
        if root == archive_folder:
            continue
        for file in files:
            file_path = os.path.join(root, file)
            file_mtime = datetime.fromtimestamp(os.path.getmtime(file_path))
            if file_mtime < cutoff_date:
                move_with_duplicate_handling(file_path, archive_folder)
                send_notification("File Archived", f"{file} moved to Archive", config["notifications"])


def monitor_folder():
    config = load_config()
    log_action(f"Monitoring folder: {config['watch_folder']}")

    while True:
        for file in os.listdir(config["watch_folder"]):
            file_path = os.path.join(config["watch_folder"], file)
            if os.path.isfile(file_path):
                organize_file(file_path, config)

        cleanup_old_files(config)
        time.sleep(5)  # check every 5 seconds


if __name__ == "__main__":
    monitor_folder()
