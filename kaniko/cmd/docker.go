package cmd

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"os"

	"github.com/spf13/cobra"
)

type DockerAuth struct {
	Auth string `json:"auth"`
}

type DockerAuths struct {
	Auths map[string]DockerAuth `json:"auths"`
}

var dockerCommand = &cobra.Command{
	Use:   "auth:docker",
	Short: "Authorise with docker registry",
	Run: func(cmd *cobra.Command, args []string) {
		path := os.Getenv("DOCKER_CONFIG")
		filepath := fmt.Sprintf("%s/config.json", path) // hack in jenkins :-(
		conf := DockerAuths{}

		if !Exists(filepath) {
			os.MkdirAll(path, 0700)
			if err := ioutil.WriteFile(filepath, []byte("{}"), 0600); err != nil {
				logger.Panic(err.Error())
			}
		} else {
			logger.Info(fmt.Sprintf("file is exists %s", filepath))
		}

		configFile, err := os.Open(filepath)
		if err != nil {
			logger.Error(err.Error())
		}
		defer configFile.Close()

		parser := json.NewDecoder(configFile)
		parser.Decode(&conf)

		if conf.Auths == nil {
			conf.Auths = make(map[string]DockerAuth)
		}

		conf.Auths[registry] = DockerAuth{
			Auth: base64.StdEncoding.EncodeToString([]byte(fmt.Sprintf("%s:%s", username, password))),
		}

		c, err := json.Marshal(conf)
		if err != nil {
			logger.Error(err.Error())
		}

		if err := ioutil.WriteFile(filepath, []byte(string(c)), 0600); err != nil {
			logger.Panic(err.Error())
		}
	},
}
