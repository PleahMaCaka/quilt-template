package com.pleahmacaka.example_mod

import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ExampleMod : ModInitializer {

    val logger: Logger = LoggerFactory.getLogger("Example Mod")

    override fun onInitialize(mod: ModContainer) {
        logger.info("Hello Quilt world from {}!", mod.metadata()?.name())
    }

}
