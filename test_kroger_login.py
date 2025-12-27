import requests
import base64
import os

def test_login():
    # Read credentials from local.properties
    props = {}
    if os.path.exists('local.properties'):
        with open('local.properties', 'r') as f:
            for line in f:
                if '=' in line:
                    key, value = line.strip().split('=', 1)
                    props[key.strip()] = value.strip().strip('"')

    client_id = props.get('KROGER_CLIENT_ID')
    client_secret = props.get('KROGER_CLIENT_SECRET')

    print(f"Testing Login with:")
    print(f"Client ID: {client_id}")
    # Mask secret for log
    print(f"Secret: {client_secret[:5]}...{client_secret[-5:] if client_secret else ''}")

    if not client_id or not client_secret:
        print("Error: Missing credentials in local.properties")
        return

    # 1. Get Token
    print(f"\n1. Getting Token...")
    token_url = "https://api.kroger.com/v1/connect/oauth2/token"
    auth_str = f"{client_id}:{client_secret}"
    base64_str = base64.b64encode(auth_str.encode('ascii')).decode('ascii')
    
    token_resp = requests.post(token_url, headers={
        "Authorization": f"Basic {base64_str}",
        "Content-Type": "application/x-www-form-urlencoded"
    }, data={"grant_type": "client_credentials", "scope": "product.compact cart.basic:write"})
    
    if token_resp.status_code != 200:
        print(f"Token Failed: {token_resp.text}")
        return

    token = token_resp.json()['access_token']
    print("Token obtained.")

    # 2. Get Cart
    print(f"\n2. Fetching Cart...")
    cart_url = "https://api.kroger.com/v1/cart"
    cart_resp = requests.get(cart_url, headers={"Authorization": f"Bearer {token}"})
    
    print(f"Cart Response Code: {cart_resp.status_code}")
    print(f"Cart Response Body: {cart_resp.text}")

if __name__ == "__main__":
    test_login()
