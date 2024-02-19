package shria.authtest.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo info) {
        if (!isAuthorized()) {
            System.out.println("You are unauthorized and author's qq is 1065737713");
            Integer crash = null;
            int b = crash;
        }
    }

    private static boolean isAuthorized() {
        String authUrl = "https://521github.com/extdomains/raw.githubusercontent.com/shria-b/auth_test_mod/main/userlist.txt";
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            Session session = client.getSession();
            String playerName = session.getUsername();

            URL url = new URL(authUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals(playerName)) {
                        reader.close();
                        return true; // 名字匹配成功，认证成功
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // 认证失败
    }
}