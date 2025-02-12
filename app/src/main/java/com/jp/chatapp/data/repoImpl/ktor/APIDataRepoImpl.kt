package com.jp.chatapp.data.repoImpl.ktor

//import com.jp.chatapp.domain.models.user.User
import com.jp.chatapp.data.utils.URL
import com.jp.chatapp.domain.models.LoginRequest
import com.jp.chatapp.domain.models.contactList.ContactRequest
import com.jp.chatapp.domain.models.contactList.ContactRes
import com.jp.chatapp.domain.models.contactList.SingleContact
import com.jp.chatapp.domain.models.user2.ProfileUpdateResponse
import com.jp.chatapp.domain.models.user2.User
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.state.ResultState
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class APIDataRepoImpl() : APIDataRepo {

    override fun register(phone: String, file: ByteArray, bio: String): Flow<ResultState<User>> =
        flow {
            emit(ResultState.Loading)

            try {


                val res = HttpClient.client.submitFormWithBinaryData<User>(
                    url = "$URL/user/register",
                    formData {
                        append("profileImg", file, Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"${UUID.randomUUID()}\""
                            )
                        })
                        append("phone", phone)
                        append("bio", bio)
                    })

                println("================== $res")
                emit(ResultState.Success(res))
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage!!))
            }


        }

    override fun getContacts(token: String): Flow<ResultState<ContactRes>> = flow {
        emit(ResultState.Loading)
        try {
            val res = HttpClient.client.get<ContactRes>("$URL/user/get_contacts") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization, "Bearer $token"
                )
            }
            println("Contacts :$res")
            emit(ResultState.Success(res))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }

    override fun addUser(
        phone: String,
        name: String,
        token: String
    ): Flow<ResultState<SingleContact>> =
        flow {

            emit(ResultState.Loading)

            val data = ContactRequest(name, phone)

            println(data)
            try {
                val res = HttpClient.client.post<SingleContact>("$URL/user/add_contact") {
                    contentType(ContentType.Application.Json)
                    body = data
                    header(HttpHeaders.Authorization, "Bearer $token")
                }

                println("User : $res")
                emit(ResultState.Success(res))

            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage))
            }
        }

    override fun login(phone: String): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading)
        val data = LoginRequest(phone = phone)
        println("DATA : $data")
        try {
            val res: User = HttpClient.client.post<User>("$URL/user/login") {
                contentType(ContentType.Application.Json)
                body = data
            }

            emit(ResultState.Success(res))

            println("Login $res")
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage))
        }
    }

    override fun updateProfileImg(file: ByteArray, token: String): Flow<ResultState<String>> =
        flow {
            emit(ResultState.Loading)

            try {
            val res = HttpClient.client.patch<ProfileUpdateResponse>("$URL/user/updateProfileImg"){
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }

                body =
                    MultiPartFormDataContent(formData {
                        append("file",file, Headers.build {
                            append(HttpHeaders.ContentType, "text/plain")
                            append(HttpHeaders.ContentDisposition, "filename=${UUID.randomUUID()}")
                        })
                    })

            }

                println(res)
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

}


