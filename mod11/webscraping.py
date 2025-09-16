import requests
from bs4 import BeautifulSoup
import json

def scrape_indeed_jobs(job_title, location, num_pages):
    base_url = f"https://www.indeed.com/l-pittsburgh,-pa-jobs.html?vjk=6320d769ee5f8acc{job_title.replace('', '+')}&l={location.replace('', '+')}"
jobs = []

for page in range(num_pages):
    url = f"{base_url}&start={page * 20}"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html_parser')
    job_listings = soup.find_all(class_='jobsearch-SerpJobCard')

    for job in job_listings:
        title = job.find(class_='title').text.strip()
        company = job.find(class_='company').text.strip()
        location = job.find(class_='location').text.strip()
        summary = job.find(class_='summary').text.strip()
        salary = job.find(class_='salaryText')
        salary = salary.text.strip() if salary else None

   jobs.append({
 'Title': title,
 'Company': company,
 'Location': location,
 'Summary': summary,
 'Salary': salary

})
        return jobs

def save_to_json(jobs, filename):
    with open(filename, 'w') as f:
        json.dump(jobs, f, indent=4)

if __name__ == "__main__":
    job_title = input("Enter job title: ")
    location = input("Enter location: ")
    num_pages = int(input("Enter number of pages: "))

    jobs = scrape_indeed_jobs(job_title, location, num_pages)
    save_to_json(jobs, 'jobs.json')
    print(f"Scraped {len(jobs)} job listings. Saved to jobs.json")
