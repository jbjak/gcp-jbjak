/**
 * Triggered from a message on a Cloud Pub/Sub topic.
 *
 * @param {!Object} event The Cloud Functions event.
 * @param {!Function} The callback function.
 */
exports.subscribe = (event, callback) => {
  // The Cloud Pub/Sub Message object.
  const pubsubMessage = event.data;

  // We're just going to log the message to prove that
  // it worked.
  console.log(Buffer.from(pubsubMessage.data, 'base64').toString());

  // Imports the Google Cloud client library
  const BigQuery = require('@google-cloud/bigquery');
  // [END bigquery_simple_app_deps]

  // [START bigquery_simple_app_client]
  const projectId = "iot-fitness-202813";

  // Creates a client
  const bigquery = new BigQuery({
    projectId: projectId,
  });
  // [END bigquery_simple_app_client]

  // Parse the CSV and build the query
  var msgData = Buffer.from(pubsubMessage.data, 'base64');
  console.log("Message: "+msgData);
  var elements = msgData.split(",");
  var mid = elements[0];
  var fname = elements[1];
  var lname = elements[2];
  var values = mid + ",'" + fname + "','" + lname + "";
  console.log("VALUES: "+values);


  // [START bigquery_simple_app_query]
  // The SQL query to run
  const sqlQuery = "INSERT INTO gym_data.test (Member_ID,First_Name,Last_Name) VALUES ("+values+");";

  // Query options list: https://cloud.google.com/bigquery/docs/reference/v2/jobs/query
  // Runs the query
  bigquery.createQueryJob(
        {
          destination:  bigquery.dataset("gym_data").table("test"),
          query: sqlQuery,
          useLegacySql: false,
          writeDisposition: "WRITE_APPEND"
        },
        function(err, job) {
          if (err) {
            console.error("Something went wrong with submitting the BigQuery job. Error: ", err);
          }
          console.log("BigQuery job successfully submitted");
          callback();
        }
      );
};
