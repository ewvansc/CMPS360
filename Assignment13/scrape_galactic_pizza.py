import requests
from bs4 import BeautifulSoup

URL = "http://localhost:8000/galactic_pizza.html"

response = requests.get(URL)
soup = BeautifulSoup(response.text, "html.parser")

restaurant_name = soup.select_one("#restaurant-name").get_text(strip=True)
restaurant_address = soup.select_one("#restaurant-address").get_text(strip=True)
restaurant_phone = soup.select_one("#restaurant-phone").get_text(strip=True)
restaurant_hours = soup.select_one("#restaurant-hours").get_text(strip=True)

pizzas = []
for card in soup.select(".pizza-card"):
    sku = card.get("data-sku")
    name = card.select_one(".pizza-name").get_text(strip=True)
    price = card.select_one(".pizza-price").get_text(strip=True)
    size = card.select_one(".pizza-size").get_text(strip=True)
    calories = card.select_one(".pizza-calories").get_text(strip=True)
    vegetarian = card.select_one(".pizza-vegetarian").get_text(strip=True)
    crust = card.select_one(".pizza-crust").get_text(strip=True)
    rating = card.select_one(".pizza-rating").get_text(strip=True)
    category = card.select_one(".pizza-category").get_text(strip=True)
    spice = card.select_one(".pizza-spice-level").get_text(strip=True)
    description = card.select_one(".pizza-description").get_text(strip=True)

    pizzas.append({
        "sku": sku,
        "name": name,
        "price": price,
        "size": size,
        "calories": calories,
        "vegetarian": vegetarian,
        "crust": crust,
        "rating": rating,
        "category": category,
        "spice": spice,
        "description": description
    })

print(restaurant_name)
print(restaurant_address)
print(restaurant_phone)
print(restaurant_hours)
print()

for p in pizzas:
    print(p)
