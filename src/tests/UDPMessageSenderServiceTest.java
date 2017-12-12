package tests;
import com.adribast.clavarnak.sender_receiver.UDPMessageSenderService;
import org.junit.Test;

import static org.junit.Assert.*;

public class UDPMessageSenderServiceTest {


    @Test
    public void broadcastAddrTest () {
        String broadcast = new UDPMessageSenderService().broadcastAddressBuilder();
        System.out.println(broadcast);
    }
}