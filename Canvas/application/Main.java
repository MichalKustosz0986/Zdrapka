package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		try {
			Group root = new Group();
			Scene scene = new Scene(root, 768, 256);

			Canvas canvas = new Canvas(768, 256);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			root.getChildren().add(canvas);

			Image image = new Image(getClass().getResourceAsStream("lenna256px.png"));
			gc.drawImage(image, 0, 0);

			int height = (int) image.getHeight();
			int width = (int) image.getWidth();

			PixelReader reader = image.getPixelReader();
			WritableImage dstImage = new WritableImage(width, height);
			WritableImage dstImage2 = new WritableImage(width, height);
			PixelWriter writer = dstImage.getPixelWriter();
			PixelWriter writer2 = dstImage2.getPixelWriter();

			try {

				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Color color = reader.getColor(x, y);
						// writer.setColor(x, y, Color.color(color.getRed()/2,
						// 0, 0));
						writer.setColor(x, y, Color.color(255, 255, 255));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			gc.drawImage(dstImage, 256, 0);

			canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {

				System.out.println("x= " + event.getX() + "y=" + event.getY());
				Color color = reader.getColor((int) event.getX(), (int) event.getY());
				writer2.setColor((int) event.getX(), (int) event.getY(), color);
				gc.drawImage(dstImage2, 256, 0);

			});

			// ======================================================================================================
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
