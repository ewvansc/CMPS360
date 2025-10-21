using System;
using System.Collections.Generic;

namespace TodoApp
{
   
    public class Task
    {
        public int Id { get; set; }
        public string Description { get; set; } = string.Empty;
        public bool IsCompleted { get; set; }
    }

    class Program
    {
        
        private static readonly List<Task> _tasks = new();
        private static int _nextId = 1;

        static void Main()
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8; 
            RunMainMenu();
        }

        private static void RunMainMenu()
        {
            while (true)
            {
                PrintHeader("To-Do List");
                Console.WriteLine("1) Add a Task");
                Console.WriteLine("2) View Tasks");
                Console.WriteLine("3) Remove a Task");
                Console.WriteLine("4) Exit");
                Console.Write("Select an option (1-4): ");

                string? choice = Console.ReadLine();
                Console.WriteLine();

                switch (choice)
                {
                    case "1":
                        AddTask();
                        break;
                    case "2":
                        ViewTasks();
                        break;
                    case "3":
                        RemoveTaskByIndex();
                        break;
                    case "4":
                        Console.WriteLine("Goodbye!");
                        return;
                    default:
                        WriteWarning("Invalid option. Please enter 1, 2, 3, or 4.");
                        Pause();
                        break;
                }

                Console.Clear();
            }
        }

        private static void AddTask()
        {
            PrintHeader("Add a Task");
            Console.Write("Enter a task description: ");
            string? description = Console.ReadLine();

            if (string.IsNullOrWhiteSpace(description))
            {
                WriteWarning("You cannot add an empty task.");
                Pause();
                return;
            }

            var task = new Task
            {
                Id = _nextId++,
                Description = description.Trim(),
                IsCompleted = false
            };

            _tasks.Add(task);
            Console.WriteLine($"Added task #{task.Id}: {task.Description}");
            Pause();
        }

        private static void ViewTasks()
        {
            PrintHeader("Your Tasks");

            if (_tasks.Count == 0)
            {
                Console.WriteLine("No tasks yet. Add one from the main menu.");
                Pause();
                return;
            }

            
            for (int i = 0; i < _tasks.Count; i++)
            {
                var t = _tasks[i];
                string completedFlag = t.IsCompleted ? "[x]" : "[ ]";
                Console.WriteLine($"{i + 1}. {completedFlag} {t.Description}  (Id: {t.Id})");
            }

            Console.WriteLine();
            
            Console.Write("Enter a number to toggle completed (or press Enter to return): ");
            string? line = Console.ReadLine();
            if (string.IsNullOrWhiteSpace(line))
            {
                return;
            }

            if (int.TryParse(line, out int index))
            {
                if (index < 1 || index > _tasks.Count)
                {
                    WriteWarning("That number is not in the list.");
                }
                else
                {
                    _tasks[index - 1].IsCompleted = !_tasks[index - 1].IsCompleted;
                    Console.WriteLine($"Toggled completion: {_tasks[index - 1].Description}");
                }
            }
            else
            {
                WriteWarning("Please enter a valid number.");
            }

            Pause();
        }

        private static void RemoveTaskByIndex()
        {
            PrintHeader("Remove a Task");

            if (_tasks.Count == 0)
            {
                Console.WriteLine("Nothing to remove; your list is empty.");
                Pause();
                return;
            }

            
            for (int i = 0; i < _tasks.Count; i++)
            {
                Console.WriteLine($"{i + 1}. {_tasks[i].Description}");
            }

            Console.WriteLine();
            Console.Write("Enter the number of the task to remove: ");
            string? input = Console.ReadLine();

            if (!int.TryParse(input, out int index))
            {
                WriteWarning("Please enter a valid number.");
                Pause();
                return;
            }

            if (index < 1 || index > _tasks.Count)
            {
                WriteWarning("That number is not in the list.");
                Pause();
                return;
            }

            var removed = _tasks[index - 1];
            _tasks.RemoveAt(index - 1);
            Console.WriteLine($"Removed: {removed.Description}");
            Pause();
        }

        

        private static void PrintHeader(string title)
        {
            Console.WriteLine(title);
            Console.WriteLine(new string('=', Math.Max(3, title.Length)));
        }

        private static void Pause()
        {
            Console.WriteLine();
            Console.Write("Press Enter to continue...");
            Console.ReadLine();
        }

        private static void WriteWarning(string message)
        {
            var prevColor = Console.ForegroundColor;
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine(message);
            Console.ForegroundColor = prevColor;
        }
    }
}
