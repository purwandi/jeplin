package cmd

import (
	"encoding/base64"
	"fmt"
	"os"

	"github.com/spf13/cobra"
	"go.uber.org/zap"
)

var (
	username  string
	password  string
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

	rootCmd.PersistentFlags().StringVarP(&username, "username", "u", "", "The username (required)")
	rootCmd.PersistentFlags().StringVarP(&password, "password", "p", "", "The password (required)")
	rootCmd.PersistentFlags().StringVarP(&registry, "registry", "r", "", "The registry (required)")
	rootCmd.PersistentFlags().StringVarP(&workspace, "workspace", "w", os.Getenv("WORKSPACE"), "The workspace directory")

	rootCmd.MarkPersistentFlagRequired("username")
	rootCmd.MarkPersistentFlagRequired("password")
	rootCmd.MarkPersistentFlagRequired("registry")

	if err := rootCmd.Execute(); err != nil {
		logger.Error(err.Error())
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
