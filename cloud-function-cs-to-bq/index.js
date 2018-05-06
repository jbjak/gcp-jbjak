/**
 * Triggered from a message on a Cloud Storage bucket.
 *
 * @param {!Object} event The Cloud Functions event.
 * @param {!Function} The callback function.
 */
exports.processFile = (event, callback) => {
  console.log('Processing file: ' + event.data.name);

// Imports the Google Cloud client libraries
const BigQuery = require('@google-cloud/bigquery');
const Storage = require('@google-cloud/storage');

/**
 * TODO(developer): Uncomment the following lines before running the sample.
 */
const projectId = "iot-fitness-202813";
const datasetId = "gym_data";
const tableId = "activities";

/**
 * This sample loads the CSV file at
 * https://storage.googleapis.com/cloud-samples-data/bigquery/us-states/us-states.csv
 *
 * TODO(developer): Replace the following lines with the path to your file.
 */
const bucketName = 'iot-fitness-uploads';
const fileName = event.data.name;

// Instantiates clients
const bigquery = new BigQuery({
  projectId: projectId,
});

const storage = new Storage({
  projectId: projectId,
});

// Configure the load job. For full list of options, see:
// https://cloud.google.com/bigquery/docs/reference/rest/v2/jobs#configuration.load
const metadata = {
  sourceFormat: 'CSV',
  skipLeadingRows: 1,
  schema: {
    fields: [
      {name: 'Member_ID', type: 'FLOAT'},
      {name: 'First_Name', type: 'STRING'},
      {name: 'Last_Name', type: 'STRING'},
      {name: 'Gender', type: 'STRING'},
      {name: 'Age', type: 'INTEGER'},
      {name: 'Height', type: 'INTEGER'},
      {name: 'Weight', type: 'INTEGER'},
      {name: 'Hours_Sleep', type: 'INTEGER'},
      {name: 'Calories_Consumed', type: 'INTEGER'},
      {name: 'Exercise_Calories_Burned', type: 'INTEGER'},
      {name: 'Date', type: 'DATE'},
      {name: 'Activity_Type', type: 'STRING'},
      {name: 'Equipment_ID', type: 'INTEGER'},
      {name: 'Activity_Code', type: 'INTEGER'},
    ],
  },
};

// Loads data from a Google Cloud Storage file into the table
bigquery
  .dataset(datasetId)
  .table(tableId)
  .load(storage.bucket(bucketName).file(fileName), metadata)
  .then(results => {
    const job = results[0];

    // load() waits for the job to finish
    //assert.equal(job.status.state, 'DONE');
    console.log(`Job ${job.id} completed.`);

    // Delete the file after processed
    var myBucket = storage.bucket(bucketName);
    var file = myBucket.file(fileName);
    file.delete();

    // Check the job's status for errors
    const errors = job.status.errors;
    if (errors && errors.length > 0) {
      throw errors;
    }
  })
  .catch(err => {
    console.error('ERROR:', err);
  });

  callback();
};
