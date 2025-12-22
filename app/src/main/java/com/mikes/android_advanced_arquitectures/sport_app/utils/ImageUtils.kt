package com.mikes.android_advanced_arquitectures.sport_app.utils

// Función helper para mapear el sportKey a URLs de imágenes
fun getSportImageUrl(sportKey: Int): String {
    return when (sportKey) {
        1 -> "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=400" // Fútbol
        2 -> "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400" // Levantamiento de Pesas
        3 -> "https://images.unsplash.com/photo-1518611012118-696072aa579a?w=400" // Gimnasia Rítmica
        4 -> "https://images.unsplash.com/photo-1530549387789-4c1017266635?w=400" // Polo Acuático
        5 -> "https://images.unsplash.com/photo-1566577739112-5180d4bf9390?w=400" // Béisbol
        6 -> "https://images.unsplash.com/photo-1486286701208-1d58e9338013?w=400" // Rugby
        7 -> "https://images.unsplash.com/photo-1622279457486-62dcc4a431d6?w=400" // Tenis
        else -> "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=400" // Deporte genérico
    }
}
