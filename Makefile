GRADLE := ./gradlew --no-daemon
EXAMPLE_BIN := example/build/install/example/bin/example
FIND_JAVA_HOME = $(shell /usr/libexec/java_home -f -v $(1))
JAVA8_HOME := $(call FIND_JAVA_HOME,1.8)
JAVA11_HOME := $(call FIND_JAVA_HOME,11)

.PHONY: test
test: test-apple test-java9

.PHONY: test-apple
test-apple: $(EXAMPLE_BIN)
	JAVA_HOME=$(JAVA8_HOME) $(GRADLE) :lib-apple:test
	JAVA_HOME=$(JAVA8_HOME) $(EXAMPLE_BIN)

.PHONY: test-java9
test-java9: $(EXAMPLE_BIN)
	JAVA_HOME=$(JAVA11_HOME) $(GRADLE) test
	JAVA_HOME=$(JAVA11_HOME) $(EXAMPLE_BIN)

.PHONY: clean
clean:
	$(GRADLE) :example:clean

$(EXAMPLE_BIN):
	JAVA_HOME=$(JAVA11_HOME) $(GRADLE) :example:install
