package wtf.choco.veinminer.api;

import java.util.function.Predicate;

import com.google.common.base.Predicates;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the different methods of activating VeinMiner.
 */
public enum ActivationStrategy {

	/**
	 * Activated when a Player is holding sneak.
	 */
	SNEAK(Player::isSneaking),

	/**
	 * Activated when a Player is standing up (i.e. not sneaking).
	 */
	STAND(Predicates.not(Player::isSneaking)),

	/**
	 * Always activated.
	 */
	ALWAYS(Predicates.alwaysTrue());

	private final Predicate<Player> condition;

	private ActivationStrategy(@NotNull Predicate<Player> condition) {
		this.condition = condition;
	}

	/**
	 * Check whether a Player is capable of vein mining according to this activation.
	 *
	 * @param player the player to check
	 *
	 * @return true if valid to vein mine, false otherwise
	 */
	public boolean isValid(@NotNull Player player) {
		return player != null && player.isValid() && this.condition.test(player);
	}

	/**
	 * Get a MineActivation based on its name.
	 *
	 * @param name the name for which to search. Case insensitive
	 *
	 * @return the resulting activation. null if none found
	 */
	@Nullable
	public static ActivationStrategy getByName(@NotNull String name) {
		for (ActivationStrategy activation : values())
			if (activation.name().equalsIgnoreCase(name)) return activation;
		return null;
	}

}