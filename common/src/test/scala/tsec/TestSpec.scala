package tsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

trait TestSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks
