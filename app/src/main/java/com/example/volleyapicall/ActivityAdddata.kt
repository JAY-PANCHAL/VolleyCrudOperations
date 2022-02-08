package com.example.volleyapicall

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class ActivityAdddata : AppCompatActivity() {
    private var edName: EditText? = null
    private var edEmail: EditText? = null
    private var btnAddData1: Button? = null
    private var edContact: EditText? = null
    private var btnupdatedata: ImageView? = null
    private var btndeleteData: ImageView? = null
    private var layoutId: LinearLayout? = null
    private var edEnterID: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adddata)
        edName = findViewById(R.id.edLname)
        edEmail = findViewById(R.id.edEmail)
        edContact = findViewById(R.id.edcontactNo)
        btnAddData1 = findViewById(R.id.btnAddData)
        btnupdatedata = findViewById(R.id.btnupdate)
        btndeleteData = findViewById(R.id.btndelete)
        layoutId = findViewById(R.id.layoutID)
        edEnterID = findViewById(R.id.edEnterID)

        val layout = layoutId

        val name = edName
        val email = edEmail
        val contactno = edContact

        btnAddData1?.setOnClickListener {
            if (name?.text.toString().isEmpty() && email?.text.toString()
                    .isEmpty() && contactno?.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "please enter all the data", Toast.LENGTH_SHORT).show()
            } else {
                AddData(name?.text.toString(), email?.text.toString(), contactno?.text.toString())
            }
        }

        btnupdatedata?.setOnClickListener {
            val edittext = edEnterID

            if (edittext?.text.toString().isEmpty()) {
                Toast.makeText(this, "please enter id", Toast.LENGTH_SHORT).show()
            } else {
                if (name?.text.toString().isEmpty() && email?.text.toString()
                        .isEmpty() && contactno?.text.toString()
                        .isEmpty() && edittext?.text.toString().isEmpty()
                ) {
                    Toast.makeText(this, "please enter all the data", Toast.LENGTH_SHORT).show()
                } else {
                    UpdateData(
                        name?.text.toString(),
                        email?.text.toString(),
                        contactno?.text.toString(),
                        edittext?.text.toString()
                    )
                }
            }
        }

        btndeleteData?.setOnClickListener {
            val edittext = edEnterID
            if (edittext?.text.toString().isEmpty()) {
                Toast.makeText(this, "please enter id", Toast.LENGTH_SHORT).show()
            } else {
                DeleteData(edittext?.text.toString())
            }

        }
    }

    private fun UpdateData(name:String,contactno: String,email: String,id: String) {
        val loading: ProgressDialog = ProgressDialog.show(this, "Adding Item", "Please wait");

        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.PUT,
                "https://62022690b8735d00174cb7c5.mockapi.io/adddata/$id",
                object : Response.Listener<String?> {

                    override fun onResponse(response: String?) {

                        loading.dismiss()

                        Toast.makeText(this@ActivityAdddata, response, Toast.LENGTH_LONG).show()

                        val intent = Intent(applicationContext, MainActivity::class.java)

                        startActivity(intent)

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {}
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val parmas: MutableMap<String, String> = HashMap()
                    //here we pass params

                    parmas["email"] = email

                    parmas["name"] = name

                    parmas["contactno"] = contactno

                    return parmas

                }

            }

        val socketTimeOut = 5000 // u can change this .. here it is 50 seconds

        val retryPolicy: RetryPolicy =
            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy

        val queue = Volley.newRequestQueue(this)

        queue.add(stringRequest)
    }

    private fun DeleteData(id: String) {

        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.DELETE,
                "https://62022690b8735d00174cb7c5.mockapi.io/adddata/$id",
                object : Response.Listener<String?> {

                    override fun onResponse(response: String?) {

                        Toast.makeText(this@ActivityAdddata, response, Toast.LENGTH_LONG).show()

                        val intent = Intent(applicationContext, MainActivity::class.java)

                        startActivity(intent)

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {}
                }
            ) {

            }

        val socketTimeOut = 5000 // u can change this .. here it is 50 seconds

        val retryPolicy: RetryPolicy =
            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy

        val queue = Volley.newRequestQueue(this)

        queue.add(stringRequest)

    }

    private fun AddData(name: String, email: String, contactno: String) {
        val loading: ProgressDialog = ProgressDialog.show(this, "Adding Item", "Please wait");

        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.POST,
                "https://62022690b8735d00174cb7c5.mockapi.io/adddata",
                object : Response.Listener<String?> {

                    override fun onResponse(response: String?) {

                        loading.dismiss()

                        Toast.makeText(this@ActivityAdddata, response, Toast.LENGTH_LONG).show()

                        val intent = Intent(applicationContext, MainActivity::class.java)

                        startActivity(intent)

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {}
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val parmas: MutableMap<String, String> = HashMap()
                    //here we pass params

                    parmas["email"] = email

                    parmas["name"] = name

                    parmas["contactno"] = contactno

                    return parmas

                }

            }

        val socketTimeOut = 5000 // u can change this .. here it is 50 seconds

        val retryPolicy: RetryPolicy =
            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy

        val queue = Volley.newRequestQueue(this)

        queue.add(stringRequest)

    }
}