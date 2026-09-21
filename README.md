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

## Reference

This project follows the requirements of the https://roadmap.sh/projects/task-tracker
