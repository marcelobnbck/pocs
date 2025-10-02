# Smart File Manager

A Python automation tool that organizes your files automatically based on file type, size, and age.
It continuously monitors a folder (e.g., Downloads) and sorts files into categories like Images, Documents, Videos, Music, Archives, etc.

## Features:
- Custom rules via `config.json`
- Automatic file organization by extension
- Large file detection (move files above a size threshold)
- Duplicate handling (no overwrites, adds `(1), (2)...`)
- Cleanup of old files (move files older than X days to `Archive/`)
- Logging of all actions in `file_organizer.log`

## Project Structure
```markdown
smart-file-manager/
│── config.json          # User-defined rules
│── smart_manager.py     # Main script
│── file_organizer.log   # Generated log file
│── README.md            # Documentation
```

## Installation
Clone the repository:
```bash
git clone https://github.com/marcelobnbck/smart-file-manager.git
cd smart-file-manager
```

##️ Configuration

Customize `config.json` to define rules:
```json
{
"watch_folder": "C:/Users/YourName/Downloads",
"destinations": {
"Images": [".jpg", ".jpeg", ".png", ".gif"],
"Documents": [".pdf", ".docx", ".txt", ".xlsx"],
"Videos": [".mp4", ".avi", ".mov"],
"Music": [".mp3", ".wav"],
"Archives": [".zip", ".rar", ".tar", ".gz"]
},
"size_rules": {
"LargeFiles": 100000000
},
"cleanup_days": 30,
"notifications": true
}
```

- `watch_folder` → folder to monitor (e.g., Downloads)
- `destinations` → map of file categories and extensions
- `size_rules` → move files larger than X bytes to a special folder
- `cleanup_days` → move files older than X days to Archive/
- `notifications` → show desktop notifications (requires plyer)

## Usage
Run the script:
```bash
python smart_manager.py
```

The tool will:
1. Continuously monitor the folder.
2. Move new files to the correct category.
3. Handle duplicates automatically.
4. Clean up old files (based on cleanup_days).
5. Log everything in file_organizer.log.

## Example Log Output
```swift
2025-10-02 14:23:45 - Moved: C:/Users/YourName/Downloads/photo.jpg → C:/Users/YourName/Downloads/Images/photo.jpg
2025-10-02 14:24:10 - Moved: C:/Users/YourName/Downloads/report.pdf → C:/Users/YourName/Downloads/Documents/report.pdf
2025-10-02 14:25:00 - Moved: C:/Users/YourName/Downloads/bigfile.iso → C:/Users/YourName/Downloads/LargeFiles/bigfile.iso
```