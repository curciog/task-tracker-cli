# Task Tracker CLI

A simple command-line task tracker built with Java.

## Features

- Add tasks
- Update tasks
- Delete tasks
- Mark tasks as in progress
- Mark tasks as done
- List all tasks
- Filter tasks by status
- Persistent task storage using JSON
- Input validation and error handling

## Requirements

- Java 21

## Usage

### Add a task

`add "Study Java"`

### Update a task

`update 1 "Study Java Collections"`

### Delete a task

`delete 1`

### Mark a task as in progress

`mark-in-progress 1`

### Mark a task as done

`mark-done 1`

### List all tasks

`list`

### List tasks by status

`list todo`

`list in-progress`

`list done`

## Task Statuses

Tasks can have one of three statuses:

- `todo`
- `in-progress`
- `done`

New tasks are created with the `todo` status.

## Data Storage

Tasks are stored locally in a `tasks.json` file.

The file is automatically created when the application is started if it does not already exist.

Each task contains:

- ID
- Description
- Status
- Creation date
- Last update date

## Project Structure

- `Main.java` — handles CLI commands and input validation
- `Task.java` — represents a task
- `TaskService.java` — contains task operations and business logic
- `TaskRepository.java` — handles task persistence
- `TaskStatus.java` — defines the available task statuses

## Technologies

- Java
- Java Collections
- Java I/O
- Regular Expressions
- JSON
- Git
