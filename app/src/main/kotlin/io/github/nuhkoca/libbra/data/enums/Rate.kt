/*
 * Copyright (C) 2020. Nuh Koca. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nuhkoca.libbra.data.enums

import androidx.annotation.DrawableRes
import io.github.nuhkoca.libbra.R

/**
 * An enum that represents all remote currencies.
 *
 * @property resId The drawable id
 */
enum class Rate(val longName: String, @DrawableRes val resId: Int) {

    /**
     * Unknown type for undefined currency, this is to avoid crash for possible future
     * implementations
     */
    UNKNOWN("Unknown", R.drawable.ic_unknown),

    /**
     * Currency type for Australian Dollar
     */
    AUD("Australian Dollar", R.drawable.ic_aud),

    /**
     * Currency type for Bulgarian Lev
     */
    BGN("Bulgarian Lev", R.drawable.ic_bgn),

    /**
     * Currency type for Brazilian Real
     */
    BRL("Brazilian Real", R.drawable.ic_brl),

    /**
     * Currency type for Canadian Dollar
     */
    CAD("Canadian Dollar", R.drawable.ic_cad),

    /**
     * Currency type for Swiss Franc
     */
    CHF("Swiss Franc", R.drawable.ic_chf),

    /**
     * Currency type for Chinese Yuan
     */
    CNY("Chinese Yuan", R.drawable.ic_cny),

    /**
     * Currency type for Czech Koruna
     */
    CZK("Czech Koruna", R.drawable.ic_czk),

    /**
     * Currency type for Danish Krone
     */
    DKK("Danish Krone", R.drawable.ic_dkk),

    /**
     * Currency type for Euro
     */
    EUR("Euro", R.drawable.ic_eur),

    /**
     * Currency type for Pound
     */
    GBP("Pound", R.drawable.ic_gbp),

    /**
     * Currency type for Hong Kong Dollar
     */
    HKD("Hong Kong Dollar", R.drawable.ic_hkd),

    /**
     * Currency type for Croatian Kuna
     */
    HRK("Croatian Kuna", R.drawable.ic_hrk),

    /**
     * Currency type for Hungarian Forint
     */
    HUF("Hungarian Forint", R.drawable.ic_huf),

    /**
     * Currency type for Indonesian Rupiah
     */
    IDR("Indonesian Rupiah", R.drawable.ic_idr),

    /**
     * Currency type for Israeli New Shekel
     */
    ILS("Israeli New Shekel", R.drawable.ic_ils),

    /**
     * Currency type for Indian Rupee
     */
    INR("Indian Rupee", R.drawable.ic_inr),

    /**
     * Currency type for Icelandic Króna
     */
    ISK("Icelandic Króna", R.drawable.ic_isk),

    /**
     * Currency type for Japanese Yen
     */
    JPY("Japanese Yen", R.drawable.ic_jpy),

    /**
     * Currency type for South Korean Won
     */
    KRW("South Korean Won", R.drawable.ic_krw),

    /**
     * Currency type for Mexican Peso
     */
    MXN("Mexican Peso", R.drawable.ic_mxn),

    /**
     * Currency type for Malaysian Ringgit
     */
    MYR("Malaysian Ringgit", R.drawable.ic_myr),

    /**
     * Currency type for Norwegian Krone
     */
    NOK("Norwegian Krone", R.drawable.ic_nok),

    /**
     * Currency type for New Zealand Dollar
     */
    NZD("New Zealand Dollar", R.drawable.ic_nzd),

    /**
     * Currency type for Philippine Peso
     */
    PHP("Philippine Peso", R.drawable.ic_php),

    /**
     * Currency type for Poland Złoty
     */
    PLN("Poland Złoty", R.drawable.ic_pln),

    /**
     * Currency type for Romanian Leu
     */
    RON("Romanian Leu", R.drawable.ic_ron),

    /**
     * Currency type for Russian Ruble
     */
    RUB("Russian Ruble", R.drawable.ic_rub),

    /**
     * Currency type for Swedish Krona
     */
    SEK("Swedish Krona", R.drawable.ic_sek),

    /**
     * Currency type for Singapore Dollar
     */
    SGD("Singapore Dollar", R.drawable.ic_sgd),

    /**
     * Currency type for Thai Baht
     */
    THB("Thai Baht", R.drawable.ic_thb),

    /**
     * Currency type for United States Dollar
     */
    USD("United States Dollar", R.drawable.ic_usd),

    /**
     * Currency type for South African Rand
     */
    ZAR("South African Rand", R.drawable.ic_zar)
}
