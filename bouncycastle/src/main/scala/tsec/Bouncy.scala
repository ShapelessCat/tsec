package tsec

import org.bouncycastle.jce.provider.BouncyCastleProvider

import java.security.Security

trait Bouncy

object Bouncy {
  // @alexknvl
  implicit val GetSchwifty: Bouncy = {
    if (Security.getProvider("BC") == null)
      Security.addProvider(new BouncyCastleProvider())
    new Bouncy {}
  }
}
