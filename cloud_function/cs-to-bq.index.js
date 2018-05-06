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
// const projectId = "your-project-id";
// const datasetId = "my_dataset";
// const tableId = "my_table";

/**
 * This sample loads the CSV file at
 * https://storage.googleapis.com/cloud-samples-data/bigquery/us-states/us-states.csv
 *
 * TODO(developer): Replace the following lines with the path to your file.
 */
const bucketName = 'cloud-samples-data';
const filename = 'bigquery/us-states/us-states.csv';

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
      {name: 'name', type: 'STRING'},
      {name: 'post_abbr', type: 'STRING'},
    ],
  },
};

// Loads data from a Google Cloud Storage file into the table
bigquery
  .dataset(datasetId)
  .table(tableId)
  .load(storage.bucket(bucketName).file(filename), metadata)
  .then(results => {
    const job = results[0];

    // load() waits for the job to finish
    assert.equal(job.status.state, 'DONE');
    console.log(`Job ${job.id} completed.`);

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
