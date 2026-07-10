import time
import requests


BASE_URL = "http://localhost:8082"
suffix = str(int(time.time()))

ingredient = {
    "name": f"Milk-{suffix}",
    "extraPrice": 0.50,
    "available": True
}

res = requests.post(f"{BASE_URL}/api/ingredients", json=ingredient)
print(f"Create ingredient response is 200? {res.status_code == 200}")
print(res.json())
ingredient_id = res.json()["id"]


menu_item = {
    "name": f"Latte-{suffix}",
    "type": "COFFEE",
    "season": "REGULAR",
    "description": "Coffee with milk",
    "basePrice": 3.50,
    "available": True,
    "allowedIngredientIds": [ingredient_id],
    "defaultIngredientIds": [ingredient_id]
}

res = requests.post(f"{BASE_URL}/api/menu", json=menu_item)
print(f"Create menu item response is 200? {res.status_code == 200}")
print(res.json())
menu_item_id = res.json()["id"]


res = requests.get(f"{BASE_URL}/api/menu/{menu_item_id}")
print(f"Get menu item response is 200? {res.status_code == 200}")
print(res.json())


order = {
    "customerId": 1,
    "note": "No sugar",
    "loyaltyPointsUsed": 0,
    "items": [
        {
            "menuItemId": menu_item_id,
            "quantity": 2,
            "addedIngredientIds": [],
            "removedIngredientIds": []
        }
    ]
}

res = requests.post(f"{BASE_URL}/api/orders", json=order)
print(f"Create order response is 200? {res.status_code == 200}")
print(res.json())
order_id = res.json()["id"]


res = requests.get(f"{BASE_URL}/api/orders/{order_id}")
print(f"Get order response is 200? {res.status_code == 200}")
print(res.json())


status_update = {
    "status": "IN_PROGRESS"
}

res = requests.patch(f"{BASE_URL}/api/orders/{order_id}/status", json=status_update)
print(f"Update order status response is 200? {res.status_code == 200}")
print(res.json())


res = requests.patch(f"{BASE_URL}/api/orders/{order_id}/cancel")
print(f"Cancel order response is 200? {res.status_code == 200}")
print(res.json())