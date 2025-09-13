from flask import Flask
from prometheus_client import start_http_server
from src.metrics import REQUEST_TIME

app = Flask(__name__)

@app.route('/')
@REQUEST_TIME.time()
def home():
    return "Hello from my-deployment-system!"

if __name__ == '__main__':
    # Metrics exposed on port 8080
    start_http_server(8080)
    app.run(host='0.0.0.0', port=8080)