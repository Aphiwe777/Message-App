/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class MessageTest {
    
    Message messageObj = new Message();
    
    @Test
    public void testMessageLengthSuccess(){
        boolean result = messageObj.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertTrue(result);
    }
    
    
    
    
}
