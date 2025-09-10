package main

import (
  "html/template"
  "log"
  "net/http"
  "os"
)

func render(w http.ResponseWriter, file string, data any) {
  tmpl, err := template.ParseFiles("templates/" + file)
  if err != nil {
    http.Error(w, "template error", http.StatusInternalServerError)
    return
  }
  _ = tmpl.Execute(w, data)
}

func homeHandler(w http.ResponseWriter, r *http.Request)  { render(w, "index.html", nil) }
func aboutHandler(w http.ResponseWriter, r *http.Request) { render(w, "about.html", nil) }
func hobbyHandler(w http.ResponseWriter, r *http.Request) { render(w, "hobby.html", nil) }

func main() {
  http.HandleFunc("/", homeHandler)
  http.HandleFunc("/about", aboutHandler)
  http.HandleFunc("/hobby", hobbyHandler)

  fs := http.FileServer(http.Dir("static"))
  http.Handle("/static/", http.StripPrefix("/static/", fs))

  port := os.Getenv("PORT")
  if port == "" { port = "8080" }
  log.Println("Server running at http://localhost:" + port)
  log.Fatal(http.ListenAndServe(":"+port, nil))
}
