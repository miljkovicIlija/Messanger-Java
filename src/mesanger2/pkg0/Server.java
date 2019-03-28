
package mesanger2.pkg0;
import java.io.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.net.*; 


public class Server extends JFrame{
    JTextField text; 
    JTextArea area; 
    JPanel panel;
    JButton dissconect; 
    JButton reconnect;
    JButton exit; 
    ObjectInputStream input; 
    ObjectOutputStream output; 
    Socket soket; 
    ServerSocket server; 
    
    
    
    public Server(){
        super("IMessanger"); 
        setSize(700,450); 
        setResizable(false);
       
        text=new JTextField();  
        text.setEditable(false);
        text.setPreferredSize(new Dimension(700,40)); 
        add(text,BorderLayout.NORTH); 
        text.setFont(new Font("Arial",Font.PLAIN,20));  
        text.setForeground(Color.blue); 
        text.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
              try {
                posaljiPoruku(evt.getActionCommand());
                text.setText(""); 
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }); 
        
        area=new JTextArea(); 
        area.setEditable(false);
        area.setFont(new Font("Arial",Font.PLAIN,22));
        Color crvena=new Color(255,145,145); 
        area.setBackground(crvena); 
        add(area,BorderLayout.CENTER); 
        add(new JScrollPane(area)); 
        
        ImageIcon dis=new ImageIcon("C:\\Users\\uSER\\Documents\\NetBeansProjects\\Mesanger2.0\\src\\ikone\\dis.png");
        dissconect=new JButton("DISCONNECT");  
        dissconect.setEnabled(false);
        dissconect.setFont(new Font("Arial",Font.BOLD,14));
        dissconect.setIcon(dis);
        dissconect.setPreferredSize(new Dimension(233,30));
        dissconect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              
            }
        });   
        
        reconnect=new JButton("RECONNECT"); 
        reconnect.setEnabled(false);
        reconnect.setFont(new Font("Arial",Font.BOLD,14));
        ImageIcon rec=new ImageIcon("C:\\Users\\uSER\\Documents\\NetBeansProjects\\Mesanger2.0\\src\\ikone\\rec.png"); 
        reconnect.setIcon(rec);
        reconnect.setPreferredSize(new Dimension(233,30));
        reconnect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
               
            }
        }); 
        
        exit=new JButton("EXIT");   
        exit.setFont(new Font("Arial",Font.BOLD,14));
        ImageIcon ex=new ImageIcon("C:\\Users\\uSER\\Documents\\NetBeansProjects\\Mesanger2.0\\src\\ikone\\exit.png"); 
        exit.setIcon(ex);
        exit.setPreferredSize(new Dimension(233,30));
        exit.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               int a=JOptionPane.showConfirmDialog(area, "Are you sure you want to exit?",null,JOptionPane.YES_NO_OPTION);
               if(a==JOptionPane.YES_OPTION){ 
                 
                   System.exit(0);
               }
           }
        });
        panel=new JPanel(); 
        panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));         
        panel.setBackground(Color.black);
        panel.add(dissconect);
        panel.add(reconnect);
        panel.add(exit); 
        add(panel,BorderLayout.SOUTH);     
        
        setVisible(true);
    } 
    public void start() throws IOException, ClassNotFoundException{
        
        while(true){
            try{
                konekcija(); 
                strimovi(); 
                cetovanje();
            }catch(IOException ex){
                ex.printStackTrace();
            } catch(ClassNotFoundException exe){
                exe.printStackTrace();
            }finally{
                zatvoriSve();
            }
        }
    } 
    public void konekcija()throws IOException{
        server=new ServerSocket(4578,10);  
        prikaziPoruku("Waiting for someone to connect...\n"); 
        soket=server.accept();  
        prikaziPoruku("Client Connected: "+soket.getInetAddress().getHostName()+"\n");
        
    } 
    public void strimovi() throws IOException{
        
        output=new ObjectOutputStream(soket.getOutputStream()); 
        output.flush(); 
         input=new ObjectInputStream(soket.getInputStream()); 
        prikaziPoruku("Streams established...\n");
    } 
    public void cetovanje()throws IOException, ClassNotFoundException{ 
        Color zelena=new Color(140, 252, 138); 
        area.setBackground(zelena); 
        kucanje(true); 
        dissconect.setEnabled(true);
        reconnect.setEnabled(true); 
        String message="You can now start chatting! ENJOY!\n";
        prikaziPoruku(message); 
        
        do{
            try{
              message=(String)input.readObject();
                if(message.toLowerCase().contains(":)")){ 
                    message=message.replace(":)", "☺");
                    
                }
                prikaziPoruku("CLIENT :  "+message+"\n");
                
                
            } catch(IOException ex){
                ex.printStackTrace();
            } catch(ClassNotFoundException exe){
                exe.printStackTrace();
            }
        } while(!message.equals("CLIENT :  END"));
    } 
    public void posaljiPoruku(String text)throws IOException{
       try { output.writeObject(text); 
        output.flush();  
        if(text.toLowerCase().contains(":)")){
            text=text.replace(":)", "☺");
            
        }
         prikaziPoruku("SERVER :  "+text+"\n");
       } catch (IOException ex){
           ex.printStackTrace();
       }        
    } 
    public void prikaziPoruku(final String poruka)throws IOException{
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){ 
                        
                            area.append(poruka);
                    }
                }
        );
        
    } 
    public void kucanje(boolean tof){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        text.setEditable(tof);
                        area.setEditable(tof);
                    }
                }
        );
        
    } 
    
    
    public void zatvoriSve()throws IOException{
        output.close();
        input.close(); 
        soket.close(); 
        
    }
}
