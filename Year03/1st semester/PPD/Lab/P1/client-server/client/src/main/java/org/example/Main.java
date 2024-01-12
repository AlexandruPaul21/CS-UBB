package org.example;

import org.example.request.Request;
import org.example.request.RequestType;
import org.example.request.Result;
import org.example.response.Response;
import org.example.response.ResponseType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Main {
    private static final List<String> files = new ArrayList<>();
    private static final int dx = 1; // delta x
    private static final int sendUnit = 20;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        if (args.length < 1) {
            System.out.println("You have to provide the country code");
            System.exit(1);
        }
        var countryCode = Integer.parseInt(args[0]);
        obtainFileNames(countryCode);

        List<Result> buffer = new ArrayList<>();
        for (String file : files) {
            File obj = new File("client/src/main/resources/input_files/" + file);
            Scanner scanner = new Scanner(obj);

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");

                buffer.add(new Result(Integer.parseInt(data[0]), Integer.parseInt(data[1]), "C" + countryCode));

                if (buffer.size() == sendUnit) {
                    Request request = new Request(RequestType.SCORE_UPDATE, buffer, null);

                    sendRequestToServer(request);

                    buffer.clear();
                    Thread.sleep(dx * 1000);
                }
            }

            Request request = new Request(RequestType.RANKING, null, null);

            Response partialRankingResponse = sendRequestToServer(request);
            var data = partialRankingResponse.getCountryResults();
            System.out.println("Partial Ranking:");
            System.out.println(data);
        }

        Response finalRankingResponse;
        int maxRetries = 5;
        int retries = 0;

        do {
            finalRankingResponse = sendRequestToServer(new Request(RequestType.FINAL_RANKING, null, "C" + countryCode));
            if (finalRankingResponse.getResponseType() == ResponseType.SUCCESS) {
                break;
            }
            retries++;
            Thread.sleep(10000); // Wait for 10 seconds before retrying
        } while (retries < maxRetries);

        if (finalRankingResponse.getResponseType() == ResponseType.SUCCESS) {
            var data = finalRankingResponse.getCountryResults();
            System.out.println("Final Ranking:");
            System.out.println(data);
        } else {
            System.out.println("Max retries reached for final ranking");
        }
    }

    public static Response sendRequestToServer(Request request) {
        try (Socket socket = new Socket("127.0.0.1", 50000); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out.writeObject(request);
            out.flush();

            Response response = (Response) in.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void obtainFileNames(int countryCode) {
        String format = "RezultateC%d_P%d";
        for (int i = 1; i <= 10; ++i) {
            files.add(String.format(format, countryCode, i));
        }
    }
}
