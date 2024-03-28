package GameEngine.EntityManagement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

// TextureObject now extends Entity, which in turn extends Actor.
public abstract class TextureObject extends Entity implements iMovable {
    // Texture associated with this object.
    protected Texture texture;

    // Constructor to create a TextureObject with a given texture, position, and speed.
    public TextureObject(Texture texture, float x, float y, double speed) {
        super(x, y, speed); // Call to the superclass (Entity, which is an Actor) constructor.
        this.texture = texture;
        // Set the width and height based on the texture's size. Use Actor's methods for setting size.
        this.setSize(texture.getWidth(), texture.getHeight());
    }

    // Override the Actor's act method for custom update logic, such as movement.
    @Override
    public void act(float delta) {
        super.act(delta);
        // Implement movement or other game logic here. You might call move() method here.
    }

    // Draw the texture. The draw method now only needs a Batch parameter, per Actor's draw method.
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Apply the actor's color, including alpha (transparency).
        batch.setColor(this.getColor().r, this.getColor().g, this.getColor().b, this.getColor().a * parentAlpha);
        // Draw the texture at the actor's position and size.
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    // Method to define movement behavior. This method needs to be implemented by subclasses.
    @Override
    public abstract void move();

    // Get bounding box for collision detection, leveraging Actor's position and size.
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    // Dispose method to clean up the texture when it's no longer needed.
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
