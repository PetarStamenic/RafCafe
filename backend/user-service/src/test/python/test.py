import requests


reg = {
    "username": "stiboly",
    "password": "xx123123123xx",
    "email": "landric6124si@raf.rs",
}
res = requests.post("http://localhost:8081/api/v1/users", json=reg)
print(f"Response is 201? {res.status_code == 201}")
print(res.json())

login = {
    "username": "stiboly",
    "password": "xx123123123xx"
}
res = requests.post("http://localhost:8081/api/v1/users/login", json=login)
print(f"Response is 200? {res.status_code == 200}")
print(res.json())
token = res.json()["jwt"]

res = requests.get(
    "http://localhost:8081/api/v1/users/1",
    headers={"Authorization": f"Bearer {token}"}
)
print(f"Response is 200? {res.status_code == 200}")
print(res.json())

update = {
    "username": "stiboly",
    "oldPassword": "xx123123123xx",
    "newPassword": "xx123123123xx333",
    "firstName": "Lav",
    "lastName": "Andric",
}
res = requests.put(
    "http://localhost:8081/api/v1/users/1",
    json=update,
    headers={"Authorization": f"Bearer {token}"}
)
print(f"Response is 200? {res.status_code == 200}")
print(res.json())

res = requests.delete(
    "http://localhost:8081/api/v1/users/1",
    headers={"Authorization": f"Bearer {token}"}
)
print(f"Response is 204? {res.status_code == 204}")
