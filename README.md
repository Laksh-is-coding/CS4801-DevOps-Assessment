# CS4801 DevOps Lab Assessment — Question Set G

**LAKSHMANAN THIYAGARAJAN — 23011102042**

## TASK 1 — Git Workflow

Created the feature branch `feature/cs4801-question-set-g` from `main`. Made separate commits for Maven refactoring, the capacity defect fix, the prerequisite change and the Jenkins pipeline. The completed work was integrated into `main`.

**Commands used:**

```powershell
git switch -c feature/cs4801-question-set-g
```
Creates a separate feature branch to work on the assessment without directly modifying `main`.

```powershell
git branch
```
Lists local branches and helps verify the current branch.

```powershell
git add .
```
Stages the modified and new files for the next commit.

```powershell
git commit -m "Fix course capacity boundary and add tests"
```
Saves the staged changes as a commit with a descriptive message.

```powershell
git log --oneline --graph --decorate --all
```
Displays commit history and branch relationships in a compact graph.

```powershell
git status
```
Checks for uncommitted changes and confirms whether the working tree is clean.

## TASK 2 — Maven Project

Converted the project into a multi-module Maven project. The parent POM manages shared properties, JUnit dependency versions and plugin versions. The child module, `course-registration-app`, contains the application source code and tests.

**Commands used:**

```powershell
mvn clean test
```
Removes previous build outputs, compiles the project and runs the test suite.

```powershell
mvn package
```
Builds the project, runs the tests and packages the application into a JAR.

The latest test run completed with **14 tests, 0 failures, 0 errors and 0 skipped tests**. Both Maven commands completed successfully. Detailed test reports are available in `course-registration-app/target/surefire-reports/`.

## TASK 3 — Course Capacity

Fixed the capacity condition in `RegistrationService.java` so that registration is rejected when current enrollment reaches the course capacity. Added tests for a full course and a course with one seat remaining. The tests also verify that rejected registration does not change student credits, registrations or course enrollment.

**Commands used:**

```powershell
mvn clean test
```
Verifies that the capacity fix and related test cases pass.

```powershell
mvn package
```
Confirms that the project builds and can be packaged successfully after the changes.

```powershell
git add course-registration-app/src/main/java/com/university/RegistrationService.java course-registration-app/src/test/java/com/university/RegistrationServiceTest.java
```
Stages the capacity implementation and its corresponding tests.

```powershell
git commit -m "Fix course capacity boundary and add tests"
```
Records the capacity fix and test additions in Git history.

## TASK 4 — Question Set G

Updated `Student.java` so that completing `CS3A1` satisfies a prerequisite specified as `CS301`. Added tests for prerequisite acceptance and rejection. The change is limited to prerequisite checking, so the two course codes remain distinct for duplicate-registration purposes.

**Commands used:**

```powershell
mvn clean test
```
Checks that the prerequisite changes work correctly and that existing tests still pass.

```powershell
mvn package
```
Verifies that the updated project builds and packages successfully.

```powershell
git add course-registration-app/src/main/java/com/university/Student.java course-registration-app/src/test/java/com/university/RegistrationServiceTest.java
```
Stages the prerequisite implementation and its related test cases.

```powershell
git commit -m "Support Question Set G prerequisite equivalence"
```
Records the prerequisite change in a separate commit.

## TASK 5 — Jenkins Pipeline

Created a Jenkins Declarative Pipeline in `Jenkinsfile` using Windows `bat` commands. It checks out the source, runs Maven tests, packages the application, publishes JUnit reports and archives the generated JAR. Packaging and archiving are skipped if the test stage fails. The `post` section publishes available test reports even after a failure.

**Commands used in the pipeline:**

```powershell
mvn -B clean test
```
Runs the test suite in batch mode, making the build suitable for automated CI execution.

```powershell
mvn -B package -DskipTests
```
Packages the application without rerunning tests after the separate test stage has succeeded.

The pipeline configuration is complete, but a local Jenkins execution has not been verified.

## TASK 6 — Kubernetes Deployment

Used Minikube with Docker to deploy NGINX using the `nginx:1.27` image. Created a Deployment with two replicas and a NodePort Service, scaled it to three replicas, and deleted a running Pod to verify that Kubernetes created a replacement.

The files `deployment.yaml` and `service.yaml` contain the Deployment and Service configurations.

**Commands used:**

```powershell
minikube start --driver=docker
```
Starts a local Kubernetes cluster using Docker as the Minikube driver.

```powershell
kubectl apply -f deployment.yaml
```
Creates or updates the NGINX Deployment using the configuration in the YAML file.

```powershell
kubectl apply -f service.yaml
```
Creates or updates the NodePort Service to expose the NGINX application.

```powershell
kubectl scale deployment nginx-deployment --replicas=3
```
Changes the desired number of NGINX Pods from two to three.

```powershell
kubectl rollout status deployment/nginx-deployment
```
Checks whether the Deployment has completed its rollout successfully.

```powershell
kubectl get deployments
```
Displays Deployment status and the desired, current and available replica counts.

```powershell
kubectl get services
```
Displays the Kubernetes Services and their exposed ports.

```powershell
kubectl get pods
```
Lists Pods and their current status to check whether the replicas are running.

```powershell
kubectl delete pod <POD_NAME>
```
Deletes a selected Pod to test whether the Deployment controller creates a replacement. Replace `<POD_NAME>` with the actual Pod name.

```powershell
minikube service nginx-deployment --url
```
Provides a URL for accessing the NGINX service through Minikube.

The Deployment controller maintains the desired replica count by creating replacement Pods when a Pod is deleted.

## TASK 7 — Documentation and Evidence

This README documents the project setup, Git workflow, Maven configuration, Jenkins pipeline, Kubernetes deployment and code changes. Terminal screenshots and command outputs are maintained separately in the `screenshots` folder as assessment evidence.

## MODIFIED FILES

- `pom.xml`
- `course-registration-app/pom.xml`
- `course-registration-app/src/main/java/com/university/RegistrationService.java`
- `course-registration-app/src/main/java/com/university/Student.java`
- `course-registration-app/src/test/java/com/university/RegistrationServiceTest.java`
- `Jenkinsfile`
- `deployment.yaml`
- `service.yaml`
- `README.md`
- `screenshots/`

## LIMITATIONS


- Maven results reflect the latest successful run; detailed results are available in the Surefire reports.
- Kubernetes deployment and self-healing evidence should be supported by the actual command outputs and screenshots collected during testing.