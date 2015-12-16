package time.api.input;
/**
 * An enumerator for all the possible key states a virtual key can have.
 * <p>
 * Up - The key is up, nothing is happening to the key
 * </p>
 * <p>
 * Down - The key is held down
 * </p>
 * <p>
 * Released - The key was released this frame
 * </p>
 * <p>
 * Pressed - The key was pressed down
 * </p>
 * @author Eddie-Boi
 *
 */
public enum KeyState {
	Up, 
	Down, 
	Released,
	Pressed;
}
