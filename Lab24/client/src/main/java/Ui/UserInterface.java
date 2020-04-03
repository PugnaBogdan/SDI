package Ui;

import Controller.ClientControllerClient;
import Entities.Client;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;


public class UserInterface {
    private ClientControllerClient clientController;
    private static Scanner input = new Scanner(System.in);

    public UserInterface(ClientControllerClient clientController) {
        this.clientController = clientController;
    }


    public void addClient(String[] arguments)
    {
        try{
            Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.clientController.addClient(newClient).thenAccept(
                    response -> System.out.println("Client added")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClient(String[] arguments)
    {
        try{
            Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.clientController.updateClient(newClient).thenAccept(
                    response -> System.out.println("Client update")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            clientController.deleteClient(id).thenAccept(
                    response -> System.out.println("Client deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void printClients(String[] arguments)
    {
        clientController.getAllClients().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    public void filterClientsId(String[] arguments)
    {
        clientController.filterClientsId().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    public void filterClientsName(String[] arguments)
    {

        int length = Integer.parseInt(arguments[3]);
        clientController.filterClientsName(length).thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    public void run() throws SQLException, ParserConfigurationException, SAXException, IOException, TransformerException {
        String command;

        while(true)
        {
            command = input.nextLine();
            String[] arguments =  Arrays.stream(command.split(" ")).toArray(String[]::new);

            if(arguments[0].equals("add"))
            {
                if(arguments[1].equals("client"))
                {
                    addClient(arguments);
                }
                else
                {
                    System.out.println("idk who's that ¯\\_(ツ)_/¯");
                }
            }
            else if(arguments[0].equals("print"))
            {
                if(arguments[1].equals("clients"))
                {
                    printClients(arguments);
                }
                else
                {
                    System.out.println("idk who's that ¯\\_(ツ)_/¯");
                }
            }
            else if(arguments[0].equals("update"))
            {
                if(arguments[1].equals("client"))
                {
                    updateClient(arguments);
                }
                else
                {
                    System.out.println("idk who's that ¯\\_(ツ)_/¯");
                }
            }
            else if(arguments[0].equals("delete"))
            {
                if(arguments[1].equals("client"))
                {
                    deleteClient(arguments);
                }
                else
                {
                    System.out.println("idk who's that ¯\\_(ツ)_/¯");
                }
            }
            else if(arguments[0].equals("filter"))
            {
                if(arguments[1].equals("client"))
                {
                   if(arguments[2].equals("id"))
                   {
                       filterClientsId(arguments);
                   }
                   else if(arguments[2].equals("name"))
                   {
                       filterClientsName(arguments);
                   }
                   else{
                       System.out.println("can't filter with that, sorry");
                   }
                }
                else
                {
                    System.out.println("idk who's that ¯\\_(ツ)_/¯");
                }
            }

            else
            {
                System.out.println("can't do ¯\\_(ツ)_/¯");
            }
        }
    }
}
