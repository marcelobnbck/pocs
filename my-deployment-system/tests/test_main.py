import pytest
from src.main import app

@pytest.fixture
def client():
    return app.test_client()

def test_home(client):
    response = client.get('/')
    assert response.status_code == 200
    assert b"Hello from my-deployment-system!" in response.data