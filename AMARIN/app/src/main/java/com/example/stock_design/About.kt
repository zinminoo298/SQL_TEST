package com.example.stock_design

import android.icu.util.Calendar
import android.icu.util.RangeValueIterator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element as Element1

class About : AppCompatActivity() {

    internal lateinit var ads:Element1
    internal lateinit var copyright:Element1
    internal lateinit var line:Element1
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_about)

        ads = Element1()
        ads!!.setTitle("About")

        val page:View = AboutPage(this).isRTL(false)
            .setImage(R.drawable.circle)
            .setDescription("This stock count application is powered by Planet Barcode.Co.Ltd according to the requirements of Amarin Book Center.Co.Ltd")
//            .addItem(ads)
            .addItem(Element1().setTitle("Version 1.0.0"))
            .addGroup("Connect with us")
            .addEmail("zinminoo2611@gmail.com")
            .addWebsite("https://www.planetbarcode.co.th","Visit our Website at 'www.planetbarcode.co.th'")
            .addYoutube("UCAywd2SznkpP9n_xsMt35Nw")
            .addFacebook("pbc.planetbarcode")
            .addItem(line())
            .addItem(createCopyright())
            .create()

        setContentView(page)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createCopyright(): Element1
    {
        copyright = Element1()
        val copyrightstring = String.format("Copyright %d by Planet Barcode.Co.Ltd",2019)
        copyright.setTitle(copyrightstring)
        copyright.setIconDrawable(R.mipmap.ic_launcher)
        copyright.setGravity(Gravity.CENTER)
        copyright.setOnClickListener{
            Toast.makeText(this,copyrightstring,Toast.LENGTH_SHORT).show()
        }
        return copyright
    }

    fun line():Element1 {
        line = Element1()
        val lineString = "Line id : @planetbarcode "
        line.setTitle(lineString)
        line.setIconDrawable(R.mipmap.line_png)
        line.setGravity(Gravity.LEFT)

        return line
    }
}
