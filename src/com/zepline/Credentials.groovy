/*
* Variable bindings:
*    credentialId - credential id
*/

// def credentials = CredentialsProvider.lookupCredentials(
//     StandardUsernamePasswordCredentials.class, 
//     Jenkins.instance
// );
          
// def cred = credentials.findResult { it.id == credentialId ? it : null }
// def username = cred.username;
// def password = cred.password;


// def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
//         com.cloudbees.plugins.credentials.Credentials.class,
//         Jenkins.instance,
//         null,
//         null
// );

// for (c in creds) {
//   println(c.id + ": " + c.description)
// }