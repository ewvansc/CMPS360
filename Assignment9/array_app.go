package main

import "fmt"

func main() {
	const conferenceTickets int = 50
	var remainingTickets uint = 50
	conferenceName := "Bison Up Conference"
	bookings := []string{}

	fmt.Printf("Welcome to %v booking application. \nWe have a total of %v are still available. \nGet your tickets here to attend\n")

	var firstName string
	var lastName string
	var email string
	var userTickets uint

	fmt.Println("Enter your First Name: ")
	fmt.Scanln(&firstName)

	fmt.Println("Enter your Last Name: ")
	fmt.Scanln(&lastName)

	fmt.Println("Enter your Email: ")
	fmt.Scanln(&email)

	fmt.Println("Enter number of Tickets: ")
	fmt.Scanln(&userTickets)

	remainingTickets = remainingTickets - userTickets
	bookings = append(bookings, firstName+" "+lastName)

	fmt.Printf("Thanks %v %v for booking %v tickets. You will receive a confirmation email at %v\n", firstName,
		lastName, userTickets, email, remainingTickets, conferenceName, bookings)

	fmt.Printf("%v tickets remaining for %v\n", remainingTickets, conferenceName)
	fmt.Printf("These are all of our bookings: %v\n", bookings)

}
