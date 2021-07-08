package cmd

import (
	"log"

	"github.com/spf13/cobra"
)

var (
	npmAwalysAuth bool
	npmStrictSSL  bool
)

var npmCommand = &cobra.Command{
	Use:   "auth:npm",
	Short: "Authorise with npm registry",
	Run: func(cmd *cobra.Command, args []string) {
		log.Println(username)
		log.Println(password)
	},
}

func init() {
	npmCommand.PersistentFlags().BoolVar(&npmAwalysAuth, "always-auth", false, "")
	npmCommand.PersistentFlags().BoolVar(&npmStrictSSL, "strict-ssl", false, "")
}
