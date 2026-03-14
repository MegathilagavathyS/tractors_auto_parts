package com.kumaravadivel.tractorautoparts.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import kotlinx.coroutines.tasks.await

class TractorDataInitializer {
    private val firestore = FirebaseFirestore.getInstance()
    
    // Comprehensive tractor brands and models data
    private val tractorBrandsData = mapOf(
        "Mahindra" to mapOf(
            "models" to listOf(
                "265 DI", "275 DI", "275 DI TU", "415 DI", "475 DI", "475 DI XP Plus",
                "575 DI", "575 DI XP Plus", "585 DI", "595 DI", "Arjun 555 DI",
                "Arjun 605 DI", "Arjun Novo 605 DI", "Arjun Novo 605 DI-i",
                "Jivo 225 DI", "Jivo 245 DI", "Jivo 305 DI"
            )
        ),
        "Swaraj" to mapOf(
            "models" to listOf(
                "717", "724 FE", "724 XM", "733 FE", "735 FE", "735 XM", "742 FE",
                "742 XT", "744 FE", "744 XT", "855 FE", "963 FE", "969 FE", "978 FE"
            )
        ),
        "John Deere" to mapOf(
            "models" to listOf(
                "3028 EN", "3036 EN", "3036E", "5036 D", "5038 D", "5042 D", "5045 D",
                "5050 D", "5050 E", "5060 E", "5075 E", "5105", "5310", "5405", "6120 B"
            )
        ),
        "TAFE" to mapOf(
            "models" to listOf(
                "30 DI Orchard", "35 DI", "35 DI Orchard", "45 DI", "45 DI Power",
                "50 DI", "50 DI Power", "60 DI", "60 DI Power"
            )
        ),
        "Massey Ferguson" to mapOf(
            "models" to listOf(
                "1035 DI", "1035 DI Super Plus", "1035 DI Tonner", "241 DI",
                "241 DI Dynatrack", "245 DI", "245 Smart", "2635 DI", "2635 DI Super Plus",
                "7250 DI", "7250 Power Up", "9500 Smart"
            )
        ),
        "Kubota" to mapOf(
            "models" to listOf(
                "NeoStar A211N", "NeoStar A211N 4WD", "NeoStar B2441", "NeoStar B2741",
                "MU4501", "MU4501 4WD", "MU5501", "MU5501 4WD"
            )
        ),
        "New Holland" to mapOf(
            "models" to listOf(
                "3032 NX", "3037 TX", "3230 NX", "3230 TX", "3600-2 TX",
                "3600-2 Heritage Edition", "3630 TX", "3630 TX Plus", "5510", "5620 TX Plus"
            )
        ),
        "Eicher" to mapOf(
            "models" to listOf(
                "188", "241", "242", "280", "312", "333", "364", "368", "380", "480", "485", "5150", "551"
            )
        ),
        "Farmtrac" to mapOf(
            "models" to listOf(
                "Atom 22", "Atom 26", "35 Classic", "35 PowerMaxx", "42 Classic",
                "45 Classic", "45 PowerMaxx", "50 Classic", "50 PowerMaxx", "60 PowerMaxx"
            )
        ),
        "Sonalika" to mapOf(
            "models" to listOf(
                "DI 35", "DI 42", "DI 47", "DI 50", "DI 60", "DI 740 III", "DI 745 III",
                "DI 750 III", "DI 750 Sikander", "DI 55 RX"
            )
        )
    )
    
    // Comprehensive spare parts data
    private val sparePartsData = mapOf(
        "Mahindra" to mapOf(
            "models" to mapOf(
                "265 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "275 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "275 DI TU" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "415 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "475 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "475 DI XP Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "575 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "575 DI XP Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "585 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "595 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Arjun 555 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Arjun 605 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Arjun Novo 605 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Arjun Novo 605 DI-i" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Jivo 225 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Jivo 245 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Jivo 305 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Swaraj" to mapOf(
            "models" to mapOf(
                "717" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "724 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "724 XM" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "733 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "735 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "735 XM" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "742 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "742 XT" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "744 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "744 XT" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "855 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "963 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "969 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                ),
                "978 FE" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Pump", "Air Filter", "Clutch Plate",
                        "Brake Drum", "Radiator", "Piston Ring", "Cylinder Liner",
                        "Water Pump", "Starter Motor", "Alternator", "Hydraulic Pump",
                        "Steering Shaft", "Gear Box", "Battery"
                    )
                )
            )
        ),
        "John Deere" to mapOf(
            "models" to mapOf(
                "3028 EN" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "3036 EN" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "3036E" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5036 D" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5038 D" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5042 D" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5045 D" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5050 D" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5050 E" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5060 E" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5075 E" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5105" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5310" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "5405" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                ),
                "6120 B" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Cylinder Head", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Pump",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "TAFE" to mapOf(
            "models" to mapOf(
                "30 DI Orchard" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "35 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "35 DI Orchard" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "45 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "45 DI Power" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "50 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "50 DI Power" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "60 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "60 DI Power" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Massey Ferguson" to mapOf(
            "models" to mapOf(
                "1035 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "1035 DI Super Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "1035 DI Tonner" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "241 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "241 DI Dynatrack" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "245 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "245 Smart" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "2635 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "2635 DI Super Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "7250 DI" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "7250 Power Up" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "9500 Smart" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Kubota" to mapOf(
            "models" to mapOf(
                "NeoStar A211N" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "NeoStar A211N 4WD" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "NeoStar B2441" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "NeoStar B2741" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "MU4501" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "MU4501 4WD" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "MU5501" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "MU5501 4WD" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "New Holland" to mapOf(
            "models" to mapOf(
                "3032 NX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3037 TX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3230 NX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3230 TX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3600-2 TX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3600-2 Heritage Edition" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3630 TX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "3630 TX Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "5510" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "5620 TX Plus" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Eicher" to mapOf(
            "models" to mapOf(
                "188" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "241" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "242" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "280" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "312" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "333" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "364" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "368" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "380" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "480" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "485" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "5150" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "551" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Farmtrac" to mapOf(
            "models" to mapOf(
                "Atom 22" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "Atom 26" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "35 Classic" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "35 PowerMaxx" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "42 Classic" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "45 Classic" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "45 PowerMaxx" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "50 Classic" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "50 PowerMaxx" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "60 PowerMaxx" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        ),
        "Sonalika" to mapOf(
            "models" to mapOf(
                "DI 35" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 42" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 47" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 50" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 60" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 740 III" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 745 III" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 750 III" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 750 Sikander" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                ),
                "DI 55 RX" to mapOf(
                    "spare_parts" to listOf(
                        "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
                        "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
                        "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
                        "Gear Lever", "Battery"
                    )
                )
            )
        )
    )
    
    suspend fun initializeTractorData() {
        try {
            // Initialize tractor brands and models
            initializeBrandsAndModels()
            
            // Initialize spare parts
            initializeSpareParts()
            
            println("✅ Tractor data initialized successfully in Firebase")
        } catch (e: Exception) {
            println("❌ Error initializing tractor data: ${e.message}")
        }
    }
    
    private suspend fun initializeBrandsAndModels() {
        val brandsCollection = firestore.collection("tractor_brands")
        
        for ((brandName, brandData) in tractorBrandsData) {
            val brandDoc = brandsCollection.document(brandName)
            
            val brandMap = mapOf(
                "name" to brandName,
                "models" to brandData["models"]
            )
            
            brandDoc.set(brandMap).await()
            
            // Create individual model documents
            val modelsCollection = brandDoc.collection("models")
            val models = brandData["models"] as? List<String> ?: emptyList()
            
            for (modelName in models) {
                val modelDoc = modelsCollection.document(modelName)
                val modelMap = mapOf(
                    "name" to modelName,
                    "brand" to brandName,
                    "createdAt" to com.google.firebase.Timestamp.now()
                )
                modelDoc.set(modelMap).await()
            }
        }
    }
    
    private suspend fun initializeSpareParts() {
        val sparePartsCollection = firestore.collection("spare_parts")
        
        for ((brandName, brandData) in sparePartsData) {
            val brandDoc = sparePartsCollection.document(brandName)
            
            val modelsData = brandData["models"] as? Map<String, Any> ?: emptyMap()
            
            for ((modelName, modelData) in modelsData) {
                val modelDoc = brandDoc.collection("models").document(modelName)
                
                val modelInfo = modelData as? Map<String, Any> ?: emptyMap()
                val sparePartsList = modelInfo["spare_parts"] as? List<String> ?: emptyList()
                
                // Create individual spare part documents
                val partsCollection = modelDoc.collection("parts")
                
                sparePartsList.forEachIndexed { partIndex, partName ->
                    val partDoc = partsCollection.document()
                    val partMap = mapOf(
                        "name" to partName,
                        "brand" to brandName,
                        "model" to modelName,
                        "category" to when {
                            partName.contains("Filter") -> "Filters"
                            partName.contains("Piston") || partName.contains("Cylinder") -> "Engine"
                            partName.contains("Brake") -> "Brakes"
                            partName.contains("Clutch") -> "Clutch"
                            partName.contains("Radiator") || partName.contains("Water Pump") -> "Cooling"
                            partName.contains("Battery") -> "Electrical"
                            partName.contains("Starter") || partName.contains("Alternator") -> "Electrical"
                            partName.contains("Hydraulic") -> "Hydraulic"
                            partName.contains("Steering") -> "Steering"
                            partName.contains("Gear") -> "Transmission"
                            else -> "General"
                        },
                        "price" to generateRandomPrice(partName),
                        "stockQuantity" to (10..100).random(),
                        "isAvailable" to true,
                        "description" to "High quality ${partName} for ${brandName} ${modelName}",
                        "imageUrl" to "",
                        "compatibleModels" to listOf(modelName),
                        "createdAt" to com.google.firebase.Timestamp.now(),
                        "index" to partIndex
                    )
                    partDoc.set(partMap).await()
                }
            }
        }
    }
    
    private fun generateRandomPrice(partName: String): Double {
        return when {
            partName.contains("Filter") -> (50..500).random().toDouble()
            partName.contains("Piston") || partName.contains("Cylinder") -> (1000..5000).random().toDouble()
            partName.contains("Brake") -> (200..1500).random().toDouble()
            partName.contains("Clutch") -> (800..3000).random().toDouble()
            partName.contains("Radiator") -> (1500..4000).random().toDouble()
            partName.contains("Battery") -> (300..2000).random().toDouble()
            partName.contains("Starter") || partName.contains("Alternator") -> (2000..6000).random().toDouble()
            partName.contains("Hydraulic") -> (1500..8000).random().toDouble()
            partName.contains("Steering") -> (500..3000).random().toDouble()
            partName.contains("Gear") -> (1000..4000).random().toDouble()
            else -> (100..2000).random().toDouble()
        }
    }
}
