package cmd

import (
	"encoding/base64"
	"fmt"
	"log"
	"os"

	"github.com/spf13/cobra"
	"go.uber.org/zap"
)

var (
	username  string
	password  string
	location  string
	registry  string
	workspace string
	logger, _ = zap.NewProduction()
)

var rootCmd = &cobra.Command{
	Short: "Various kaniko helper to generate auths",
}

func Execute() {
	rootCmd.AddCommand(dockerCommand)
	rootCmd.AddCommand(npmCommand)

	rootCmd.PersistentFlags().StringVarP(&location, "location", "l", "", "Absolute path for storing data")
	rootCmd.PersistentFlags().StringVarP(&username, "username", "u", "", "The username")
	rootCmd.PersistentFlags().StringVarP(&password, "password", "p", "", "The password")
	rootCmd.PersistentFlags().StringVarP(&registry, "registry", "r", "", "The registry")
	rootCmd.PersistentFlags().StringVarP(&workspace, "workspace", "w", "", "The workspace directory")

	if err := rootCmd.Execute(); err != nil {
		log.Panic(err)
	}
}

func exists(name string) bool {
	if _, err := os.Stat(name); err != nil {
		if os.IsNotExist(err) {
			return false
		}
	}
	return true
}

func decodeToBase64(username, password string) string {
	return base64.StdEncoding.EncodeToString([]byte(fmt.Sprintf("%s:%s", username, password)))
}
