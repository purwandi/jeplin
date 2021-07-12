package cmd

import (
	"fmt"
	"io/ioutil"

	"github.com/spf13/cobra"
)

var (
	npmAwalysAuth bool
	npmStrictSSL  bool
)

var npmCommand = &cobra.Command{
	Use:   "auth:npm",
	Short: "Authorize with npm registry",
	Run: func(cmd *cobra.Command, args []string) {
		filepath := fmt.Sprintf("%s/.npmrc", workspace)
		npmrc := fmt.Sprintf(`
registry="%s"
always-auth=%t
strict-ssl=%t
_auth="%s"
`, registry, npmAwalysAuth, npmStrictSSL, decodeToBase64(username, password))

		if err := ioutil.WriteFile(filepath, []byte(npmrc), 0600); err != nil {
			logger.Panic(err.Error())
		}
	},
}

func init() {
	npmCommand.PersistentFlags().BoolVar(&npmAwalysAuth, "always-auth", false, "")
	npmCommand.PersistentFlags().BoolVar(&npmStrictSSL, "strict-ssl", false, "")
}
