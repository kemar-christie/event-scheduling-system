package org.ess.app.window;

import org.ess.app.common.Navigate;

import java.util.Map;

public class View {

    public static void eventFormWindow(Map<String, Object> map) {
        Navigate.toWindow("Event Form", "event_form_controller_layout.fxml", map);
    }

    public static void eventDeleteConfirmationWindow(Map<String, Object> data) {
        Navigate.toWindow("Delete Event", "event_delete_confirmation_controller_layout.fxml", data, 500, 400);
    }

    public static void assetFormWindow(Map<String, Object> map) {
        Navigate.toWindow("Asset Form", "asset_form_controller_layout.fxml", map);
    }

    public static void assetDeleteConfirmationWindow(Map<String, Object> data) {
        Navigate.toWindow("Delete Asset", "asset_delete_confirmation_controller_layout.fxml", data, 500, 400);
    }

    public static void userDeleteConfirmationWindow() {
        Navigate.toWindow("Delete User", "user_delete_confirmation_controller_layout.fxml", 500, 400);
    }

    public static void userFormWindow() {
        Navigate.toWindow("User Form", "user_form_controller_layout.fxml");
    }
}
