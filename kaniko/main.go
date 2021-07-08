package main

import (
	"github.com/joho/godotenv"
	"github.com/purwandi/kaniko/cmd"
)

func main() {
	_ = godotenv.Load()

	cmd.Execute()
}
