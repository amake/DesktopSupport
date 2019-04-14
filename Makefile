GRADLE := ./gradlew --no-daemon
EXAMPLE_BIN := example/build/install/example/bin/example
EXAMPLE_APP := example/build/install/example-app/DesktopSupportExampleApp.app
EXPECT_APP := example/expect-app
FIND_JAVA_HOME = $(shell /usr/libexec/java_home -f -v $(1))
JAVA8_HOME := $(call FIND_JAVA_HOME,1.8)
JAVA11_HOME := $(call FIND_JAVA_HOME,11)
TEST_JAVA_OPTS := -Djava.util.logging.config.file="$(PWD)/example/logging.properties"
JAVA_FILES = $(shell find . -name *.java)

export JAVA_HOME := $(JAVA11_HOME)

.PHONY: test
test: test-apple test-java9

.PHONY: test-apple
test-apple: export JAVA_HOME := $(JAVA8_HOME)
test-apple: $(EXAMPLE_BIN) $(EXAMPLE_APP)
	$(GRADLE) :lib-apple:test
	JAVA_OPTS=$(TEST_JAVA_OPTS) $(EXAMPLE_BIN)
	$(EXPECT_APP) $(PWD)/$(EXAMPLE_APP) 1.8

.PHONY: test-java9
test-java9: $(EXAMPLE_BIN) $(EXAMPLE_APP)
	$(GRADLE) test
	JAVA_OPTS=$(TEST_JAVA_OPTS) $(EXAMPLE_BIN)
	$(EXPECT_APP) $(PWD)/$(EXAMPLE_APP) 11

.PHONY: clean
clean:
	$(GRADLE) :example:clean

$(EXAMPLE_BIN): export JAVA_HOME := $(JAVA11_HOME)
$(EXAMPLE_BIN): $(JAVA_FILES)
	$(GRADLE) :example:installDist

$(EXAMPLE_APP): export JAVA_HOME := $(JAVA11_HOME)
$(EXAMPLE_APP): $(JAVA_FILES)
	$(GRADLE) :example:installAppDist

.PHONY: publish-local
publish-local:
	$(GRADLE) :lib:publishToMavenLocal
