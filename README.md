# Magic Origins Mod

A Minecraft Forge mod for version 1.21.x that automatically assigns origins to players based on predefined mappings. Compatible with the Origins mod, this mod removes player choice and assigns specific origins to specific users automatically upon login.

## Features

- **Automatic Origin Assignment**: Players are automatically assigned their origin when they log in - no selection screen
- **Predefined Mappings**: Origins are assigned based on username mappings defined in the mod
- **Origins Mod Compatibility**: Designed to work seamlessly with the Origins mod
- **Warden Loot Enhancement**: Wardens drop Totem of Undying with 100% chance
- **Player Data Persistence**: Tracks player data and progress using NBT storage
- **Server-Side Logic**: All origin assignment logic runs server-side for security

## Player-Origin Mappings

The following players have predefined origin assignments:

| Username | Origin | Magic Type |
|----------|--------|-----------|
| Fishy01003 | Water | Aquatic abilities, faster swimming |
| 52bm | Nature | Plant growth, forest bonuses |
| Green_boii | Swamp | Poison resistance, swamp mobility |
| Balo01003 | Stone | Mining bonuses, damage resistance |
| Temsync | Echo/Void | Speed bonuses, teleportation |
| Polocol | Fire | Fire resistance, flame abilities |
| Snowfester | Frost | Ice abilities, cold immunity |
| Fkoe | Amphibian | Enhanced jumping, water speed |
| _Kitax | Shadow | Invisibility in darkness, stealth |

## Installation

### Prerequisites
- Minecraft 1.21.x
- Minecraft Forge 47.3.0 or later
- Java 21
- Origins mod (optional but recommended)

### Steps
1. Download the latest release from the [Releases](https://github.com/Martin20100209/MagicOriginsMod/releases) page
2. Place the `.jar` file in your `mods` folder
3. Install the Origins mod if you want full origin functionality
4. Start your Minecraft server or client

## Development Setup

### Requirements
- Java 21 (JDK)
- IntelliJ IDEA or Eclipse
- Git

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/Martin20100209/MagicOriginsMod.git
   cd MagicOriginsMod
   ```

2. Import the project in your IDE:
   - **IntelliJ IDEA**: Open the project folder directly
   - **Eclipse**: Import as existing Gradle project

3. Run Gradle setup:
   ```bash
   ./gradlew genEclipseRuns  # For Eclipse
   ./gradlew genIntellijRuns  # For IntelliJ
   ```

4. Run the development environment:
   ```bash
   ./gradlew runClient  # For client testing
   ./gradlew runServer  # For server testing
   ```

## How It Works

### Origin Assignment Process
1. Player joins the server
2. `OriginAssignmentHandler` intercepts the login event
3. Player's username is checked against predefined mappings
4. If a mapping exists, the origin is automatically assigned
5. Assignment is stored in player's persistent NBT data
6. Origins mod integration applies the origin powers

### Warden Loot Enhancement
- `WardenLootHandler` listens for Warden death events
- Automatically adds Totem of Undying (100% drop chance) to the loot drops
- Works alongside existing Warden drops

### Data Persistence
- Player data is stored using Minecraft's persistent NBT system
- Tracks playtime, ability progress, and origin assignments
- Data persists across server restarts and player sessions

## Configuration

Currently, player-origin mappings are hardcoded in `PlayerOriginManager.java`. To add new players:

1. Edit `src/main/java/com/magicorigins/mod/PlayerOriginManager.java`
2. Add new mapping in the `initialize()` method:
   ```java
   PLAYER_ORIGIN_MAP.put("NewPlayerName", "origin_type");
   ```
3. Rebuild and restart the server

## API Integration

### Origins Mod Integration
The mod is designed to integrate with the Origins mod's API. The integration code is located in `OriginAssignmentHandler.integrateWithOriginsMod()` method.

### Adding Custom Origins
To support custom origins, modify the origin type mappings in `PlayerOriginManager.java` to use the appropriate origin identifiers.

## Building

### Development Build
```bash
./gradlew build
```

### Release Build
```bash
./gradlew build
# Output: build/libs/magicorigins-1.0.0-1.21.x.jar
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Compatibility

- **Minecraft**: 1.21.x
- **Forge**: 47.3.0+
- **Java**: 21
- **Origins Mod**: 1.13.0+ (optional)

## Support

For issues, questions, or feature requests:
- Open an issue on [GitHub Issues](https://github.com/Martin20100209/MagicOriginsMod/issues)
- Check existing issues before creating new ones
- Provide detailed information about your setup when reporting bugs

## Credits

- **Origins Mod Team**: For the original Origins mod that inspired this project
- **Minecraft Forge Team**: For the modding framework
- **MagicOrigins Mod Team**: For development and maintenance

## Changelog

### Version 1.0.0
- Initial release
- Automatic origin assignment system
- Predefined player-origin mappings
- Warden loot enhancement (100% Totem of Undying drop)
- Player data persistence
- Origins mod compatibility framework