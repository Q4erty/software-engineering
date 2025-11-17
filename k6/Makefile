# ==================================
#         K6 Load Testing
# ==================================

SCRIPT = test-get.js
K6 = k6

HOST ?= http://localhost:8080
CACHE_MODE ?= MANUAL
VUS ?= 50
DURATION ?= 30s

export HOST
export CACHE_MODE
export VUS
export DURATION

# --------------------------
#      Команды
# --------------------------

run:
	$(K6) run $(SCRIPT)

smoke:
	$(K6) run $(SCRIPT) \
		--env VUS=5 \
		--env DURATION=10s

stress:
	$(K6) run $(SCRIPT) \
		--env VUS=200 \
		--env DURATION=1m

ramp:
	$(K6) run \
		--stages "10s:10" "20s:50" "30s:200" "10s:0" \
		$(SCRIPT)

report:
	$(K6) run --out json=report.json $(SCRIPT)

clean:
	rm -f report.json
