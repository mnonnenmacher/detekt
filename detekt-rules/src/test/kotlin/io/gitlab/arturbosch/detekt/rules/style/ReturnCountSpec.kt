package io.gitlab.arturbosch.detekt.rules.style

import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Java6Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

class ReturnCountSpec : Spek({

	given("a file with 3 returns") {
		val code = """
			fun test(x: Int) {
				when (x) {
					5 -> return 5
					4 -> return 4
					3 -> return 3
				}
			}
		"""

		it("should get flagged by default") {
			val findings = ReturnCount().lint(code)
			assertThat(findings).hasSize(1)
		}

		it("should not get flagged when max value is 3") {
			val findings = ReturnCount(TestConfig(mapOf(ReturnCount.MAX to "3"))).lint(code)
			assertThat(findings).hasSize(0)
		}

		it("should get flagged when max value is 1") {
			val findings = ReturnCount(TestConfig(mapOf(ReturnCount.MAX to "1"))).lint(code)
			assertThat(findings).hasSize(1)
		}

	}

	given("a file with 2 returns") {
		val code = """
			fun test(x: Int) {
				when (x) {
					5 -> return 5
					4 -> return 4
				}
			}
		"""

		it("should not get flagged by default") {
			val findings = ReturnCount().lint(code)
			assertThat(findings).hasSize(0)
		}

		it("should not get flagged when max value is 2") {
			val findings = ReturnCount(TestConfig(mapOf(ReturnCount.MAX to "2"))).lint(code)
			assertThat(findings).hasSize(0)
		}

		it("should get flagged when max value is 1") {
			val findings = ReturnCount(TestConfig(mapOf(ReturnCount.MAX to "1"))).lint(code)
			assertThat(findings).hasSize(1)
		}

	}

})
