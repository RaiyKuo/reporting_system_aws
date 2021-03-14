# (2021-03-14)

### Bug Fixes

* **ExcelService:** Remove file name hardcoding in
  TODO ([58ba796](https://github.com/RaiyKuo/reporting_system_aws/commit/58ba796313c9bef52a097ef172b4b4a365092fd1))
* **Gloabal:** Fixed Health
  Check ([544f071](https://github.com/RaiyKuo/reporting_system_aws/commit/544f07154731c1e3c6fc73c73d7b4008d714dc28))
* **Global:** Make all three services able to work and communicate in docker
  containers. ([dd1b98d](https://github.com/RaiyKuo/reporting_system_aws/commit/dd1b98dbffc19face452596d18b947e3b957ba27))
* **PDFService:** Make PDF generation working in docker
  image ([7486a4d](https://github.com/RaiyKuo/reporting_system_aws/commit/7486a4d7d98408740630c5886aa34befe01bac2b))

### Build System

* **Global:** Build all three services into docer
  images ([503dc5e](https://github.com/RaiyKuo/reporting_system_aws/commit/503dc5e9a705d5dd3d7f5d96f2a6516e908a6023))
* **Global:** Fix few bugs about
  scripts ([84ca43e](https://github.com/RaiyKuo/reporting_system_aws/commit/84ca43e5c91c042bd915917dc1e604b88747f1dd))

### chore

* **Configs:** Move sqs queue location string into properties
  files ([ee9b318](https://github.com/RaiyKuo/reporting_system_aws/commit/ee9b3184149592c8cb0b653e177d01b2371ce94a))

### Code Refactoring

* **ClientService:** Merge redundant excel and pdf methods into
  one ([c9360cf](https://github.com/RaiyKuo/reporting_system_aws/commit/c9360cfc2924aca49350701acc59097905a0dc2e))

### Documentation

* **git:** Update CHANGELOG.md based on commit
  messages ([fc58920](https://github.com/RaiyKuo/reporting_system_aws/commit/fc5892024e9f899fd9d2c7a8bf02af0cbf4b012d))

### Features

* **Global:** Add feature to delete report and
  files ([4d9fcc4](https://github.com/RaiyKuo/reporting_system_aws/commit/4d9fcc4d5028483e3429ea4c505af58ad81111e6))
* **ClientService:** Add Multi-threading to generate report sync
  action ([1f16bc1](https://github.com/RaiyKuo/reporting_system_aws/commit/1f16bc17c48efd95a5ec9188a2d9ec43e13616fb))
* **ClientService:** Add Multi-threading to report and files
  deletion ([a397d48](https://github.com/RaiyKuo/reporting_system_aws/commit/a397d48e4b6c444985df3bc7168bad33aed0cb8f))
* **ClientService:** Change DataBase from local MySQL to AWS
  DynamoDB ([de93a44](https://github.com/RaiyKuo/reporting_system_aws/commit/de93a449e4057f7f74b092253cb063324032ec4a))
* **ClientService:** Change the DB from embedded h2 to local
  MySQL ([40c2c6f](https://github.com/RaiyKuo/reporting_system_aws/commit/40c2c6fa53bbaea6d7fdd29afc19c184e2bf3990))
* **ExcelService:** Change DataBase from local MySQL to AWS
  DynamoDB ([0bf4067](https://github.com/RaiyKuo/reporting_system_aws/commit/0bf40676b4f57a5948f535e7cf10f1e2313fef1e))
* **ExcelService:** Change datatsource from HashMap to local
  MySQL ([d29d190](https://github.com/RaiyKuo/reporting_system_aws/commit/d29d1903eb66b17f1234bc2b694458a48367dc39))
* **ExcelService:** Move Excel file location from local to S3
  bucket ([b64486b](https://github.com/RaiyKuo/reporting_system_aws/commit/b64486b3b391ee4db2735458ece57a48cd8caa75))
* **PDFService:** Change DataBase from local MySQL to AWS
  DynamoDB ([6bcdaa1](https://github.com/RaiyKuo/reporting_system_aws/commit/6bcdaa14f0dc5174a23409dad671d6958479a19b))
* **PDFService:** Change datatsource from embedded MongoDB to local
  MySQL ([e6acda2](https://github.com/RaiyKuo/reporting_system_aws/commit/e6acda27f463a30a2af06157ee76da3451981c73))

### Tests

* **ClientService:** Added test for endpoint method
  createReportDirectly ([ae5763f](https://github.com/RaiyKuo/reporting_system_aws/commit/ae5763fed4bb914137653c0c49d6caea1757f666))
* **ExcelService:** Add Controller Endpoint createExcel method
  Test ([3cad061](https://github.com/RaiyKuo/reporting_system_aws/commit/3cad0613e1de24a17ab3a077ba287e4376fdd6e4))




